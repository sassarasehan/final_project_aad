package lk.ijse.final_project_aad.service.impl;


import lk.ijse.final_project_aad.config.UserNotFoundException;
import lk.ijse.final_project_aad.dto.UserDTO;
import lk.ijse.final_project_aad.entity.User;
import lk.ijse.final_project_aad.repo.UserRepository;
import lk.ijse.final_project_aad.service.UserService;
import lk.ijse.final_project_aad.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return modelMapper.map(user, UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public UserDTO searchUser(String username) {
        return userRepository.findByEmail(username)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElse(null);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String email) {
        if (userRepository.existsByEmail(email)) {
            userRepository.deleteByEmail(email);
        } else {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userDTO.setRole(userDTO.getRole());
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getEmail(), user.getPassword(), user.getName(), user.getRole());
    }

    public int updateUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(userDTO.getName());
            user.setRole(userDTO.getRole());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            userRepository.save(user);
            return 1;
        } else {
            return 0;
        }
    }


    @Override
    public long getTotalUsers() {
        return userRepository.count();
    }

    public long countUsers() {
        return userRepository.count();
    }

}
