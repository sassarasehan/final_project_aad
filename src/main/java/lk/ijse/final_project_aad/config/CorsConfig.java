package lk.ijse.final_project_aad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/hotels/**")
                        .allowedOrigins("http://localhost:63342")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                registry.addMapping("/api/v1/rooms/**")
                        .allowedOrigins("http://localhost:63342")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                registry.addMapping("/api/v1/taxi/**")
                        .allowedOrigins("http://localhost:63342")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                registry.addMapping("/api/v1/bookings/**")
                        .allowedOrigins("http://localhost:63342")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                registry.addMapping("/api/v1/payments/**")
                        .allowedOrigins("http://localhost:63342")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                registry.addMapping("/api/v1/taxi-booking/**")
                        .allowedOrigins("http://localhost:63342")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");

                registry.addMapping("/api/v1/tour-packages/**")
                        .allowedOrigins("http://localhost:63342")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");

                registry.addMapping("/**").allowedOrigins("http://localhost:63342");

                registry.addMapping("/api/v1/user/check-email")
                        .allowedOrigins("http://localhost:63342") // Adjust for your frontend
                        .allowedMethods("GET");

            }



        };
    }
}
