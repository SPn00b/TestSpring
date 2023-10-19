package com.example.demo.keyclockdemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class IndexController {

	@GetMapping("hello-11")
	@PreAuthorize("hasRole('myuserrole')")
	public String helloUser() {
		return "Hello from spring boot 3 keyclock security hello user";
	}

	@GetMapping("/hello-22")
	@PreAuthorize("hasRole('myadminrole')")
	public String helloAdmin() {
		return "Hello from spring boot 3 keyclock security hello admin";
	}

}
