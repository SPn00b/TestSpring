package com.test.spring.security;

import java.util.Arrays;
import java.util.EnumSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.test.spring.service.SecurityUserDetailsService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.SessionTrackingMode;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private DataSource dataSource;

	public void onStartup(ServletContext servletContext) throws ServletException {
		// ...
		servletContext.getSessionCookieConfig().setHttpOnly(true);
		servletContext.getSessionCookieConfig().setSecure(true);
		servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity

				.securityContext(securityContext -> {
					securityContext.securityContextRepository(
							new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
									new HttpSessionSecurityContextRepository()));

					securityContext.requireExplicitSave(true);

				})

				.sessionManagement(session -> {
					session.maximumSessions(1).maxSessionsPreventsLogin(true);
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
					session.sessionFixation().migrateSession();
				})

				.csrf(csrf -> {
					csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
					csrf.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler());
				})

				.headers(headers -> headers.xssProtection(
						xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)))

				.formLogin().and().authorizeHttpRequests().requestMatchers("/home", "/register", "/saveUser")
				.permitAll().requestMatchers("/welcome").authenticated().requestMatchers("/admin").hasAuthority("Admin")
				.requestMatchers("/mgr").hasAuthority("Manager").requestMatchers("/emp").hasAuthority("Employee")
				.requestMatchers("/hr").hasAuthority("HR").requestMatchers("/common")
				.hasAnyAuthority("Employeee,Manager,Admin").anyRequest().authenticated()

				.and().formLogin().defaultSuccessUrl("/welcome", true)

				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).deleteCookies("JSESSIONID")
				.and().rememberMe().tokenRepository(persistentTokenRepository()).useSecureCookie(true)
				.tokenValiditySeconds(60 * 60 * 12 * 2 * 7 * 4)

				.and().exceptionHandling().accessDeniedPage("/accessDenied")

				.and().authenticationProvider(authenticationProvider())

		;

		return httpSecurity.build();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		var jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}

	@Bean
	public StrictHttpFirewall httpFirewall() {
		var firewall = new StrictHttpFirewall();
		firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST", "Delete", "PUT"));
		return firewall;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		var authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(securityUserDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		return authenticationProvider;
	}

}
