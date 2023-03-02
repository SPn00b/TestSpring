package com.test.spring.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@Configuration
public class AppConfig {

	public static final String passwordSecret = "IAduiwhr47r7T*^TsdaID&*$$51fakNOAI(";
	//public static final String passwordSecret = "test";

	@Bean
	public static PasswordEncoder passwordEncoder(/*String UUID*/) {

		var idForEncode = "pbkdf2@own";

		var pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(passwordSecret, 16, 310000,
				SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);

		// var pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(UUID, 16, 310000,
		// 		SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);

		
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(idForEncode, new BCryptPasswordEncoder());
		encoders.put("pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("pbkdf2@own", pbkdf2PasswordEncoder);
		encoders.put("scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		return new DelegatingPasswordEncoder(idForEncode, encoders);
	}

}