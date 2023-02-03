package com.test.spring.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
public class AppConfig {

	public static final String passwordSecret = "IAduiwhr47r7T*^TsdaID&*$$51fakNOAI(";

	@Bean
	public static PasswordEncoder passwordEncoder() {

		var pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(passwordSecret, 16, 310000,
				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);

		var idForEncode = "pbkdf2@own";
		Map encoders = new HashMap<>();
		encoders.put(idForEncode, new BCryptPasswordEncoder());
		// encoders.put("noop", NoOpPasswordEncoder.getInstance());
		// encoders.put("pbkdf2",
		// Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_5());
		encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("pbkdf2@own", pbkdf2PasswordEncoder);
		// encoders.put("scrypt",
		// SCryptPasswordEncoder.defaultsForSpringSecurity_v4_1());
		encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
		// encoders.put("argon2",
		// Argon2PasswordEncoder.defaultsForSpringSecurity_v5_2());
		encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		// encoders.put("sha256", new StandardPasswordEncoder());

		return new DelegatingPasswordEncoder(idForEncode, encoders);
	}

}