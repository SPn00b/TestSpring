package com.test.spring.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerUser {

    @GetMapping("/api/v1/getsomething")
    String getSomethString() {
        return "Hello Sir";
    }

}
