package com.test.spring.component;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.test.spring.pojo.User;
import com.test.spring.repository.UserRepository;

@Configuration
public class UserAddCommandLineRunner {

    Logger logger = LoggerFactory.getLogger(UserAddCommandLineRunner.class);

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            logger.info("Started!!!");
            if (userRepository.count() == 0) {
                User user = new User();
                user.setEmail("test@localhost.com");
                user.setPassword("{noop}password");
                List<String> role = new ArrayList<>();
                role.add("Admin");
                user.setRoles(role);
                user.setUsername("Test User");
                user.setAccountNonLocked(true);
                userRepository.save(user);
            }
        };
    }

}
