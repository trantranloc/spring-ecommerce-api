package com.spring.springecommerceapi;

import com.spring.springecommerceapi.model.Role;
import com.spring.springecommerceapi.model.User;
import com.spring.springecommerceapi.repository.RoleRepository;
import com.spring.springecommerceapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class SpringEcommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEcommerceApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(RoleRepository roleRepository, UserRepository userRepository) {
        return (args) -> {
            // Kiểm tra và tạo role "ROLE_ADMIN" nếu chưa có
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            // Kiểm tra và tạo admin nếu chưa có
            if (!userRepository.existsById("admin@example.com")) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode("admin");

                User admin = new User();
                admin.setEmail("admin@example.com");
                admin.setFirstName("Admin");
                admin.setLastName("Admin");
                admin.setPassword(encodedPassword);  // Mã hóa mật khẩu
                admin.setRoles(Collections.singleton(adminRole));  // Gán vai trò cho admin
                userRepository.save(admin);
            }
        };
    }
}
