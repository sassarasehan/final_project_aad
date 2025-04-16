package lk.ijse.final_project_aad.repo;


import lk.ijse.final_project_aad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

  /*  User findByEmail(String userName);*/

    boolean existsByEmail(String userName);

    // Method to delete user by email
    void deleteByEmail(String email);
    long count();
   Optional<User> findByEmail(String email);

}