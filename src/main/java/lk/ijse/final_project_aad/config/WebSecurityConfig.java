package lk.ijse.final_project_aad.config;

import lk.ijse.final_project_aad.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt is a strong hashing algorithm
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); // Use custom userDetailsService
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Providing the AuthenticationManager
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disabling CSRF for stateless APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/authenticate",     // Login endpoint
                                "/api/v1/admin/**",              // Admin routes
                                "/api/v1/user/register",         // Register new users
                                "/api/v1/user/get",              // Get user info
                                "/api/v1/user/delete/**",
                                "/api/v1/user/update/**",
                                "/api/v1/user/count/**",
                                "/api/v1/user/check-email/**",
                                "/api/v1/hotels/**",
                                "/api/v1/rooms/**",
                                "/api/v1/taxi/**",
                                "/api/v1/bookings/**",
                                "/api/v1/payments/**",
                                "/api/v1/taxi-bookings/**",
                                "/api/v1/tour-packages/**",
                                "/api/v1/auth/refreshToken",     // Refresh token endpoint
                                "/v3/api-docs/**",               // Swagger docs
                                "/swagger-ui/**",                // Swagger UI
                                "/swagger-ui.html").permitAll()  // Swagger UI page
                        .anyRequest().authenticated()    // All other requests require authentication
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless (JWT)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // Adding custom JWT filter
                .build();
    }


}
