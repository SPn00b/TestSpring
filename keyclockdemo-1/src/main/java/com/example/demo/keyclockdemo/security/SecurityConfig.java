package com.example.demo.keyclockdemo.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthConverter jwtAuthConverter;

	public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
		this.jwtAuthConverter = jwtAuthConverter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf(CsrfConfigurer::disable)
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.anyRequest().authenticated());

		httpSecurity.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
				.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));

		httpSecurity.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS));

		return httpSecurity.build();

	}
}
