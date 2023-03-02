package com.test.spring.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.spring.pojo.User;
import com.test.spring.repository.UserRepository;
import com.test.spring.service.SecurityUserDetailsService;

import jakarta.validation.Valid;

@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    String getHomePage() {
        logger.debug("Inside getHomePage()");
        return "index";
    }

    @RequestMapping("/add-user")
    ResponseEntity<User> addUser(@Valid User user, BindingResult bindingResult, Model model) {
        model.addAttribute("user", user);
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<User>) ResponseEntity.badRequest();
        }
        User u = new User();
        if (user.getEmail() != null) {
            u = userRepository.getFindUserByEmail(user.getEmail());
        }
        u.setRandomString(UUID.randomUUID().toString());

        u.setPassword(passwordEncoder.encode(user.getPassword()));
        u.setRoles(user.getRoles());
        try {
            securityUserDetailsService.createUser(u);
        } catch (Exception e) {
            return (ResponseEntity<User>) ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(u);
    }

}
