package com.securitydemo.config;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.securitydemo.service.UserService;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				userService
					.getAllUsers()
					.stream()
					.map(user -> User
							.withUsername(user.getUserName())
							.password(passwordEncoder().encode(user.getUserPassword()))
							.roles(user.getUserRole())
							.build())
					.collect(Collectors.toList())
				);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers("/admins")
			.hasRole("ADMIN")
			.requestMatchers("/")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
//			.formLogin()
			.httpBasic();
		// either we can use httpBasic() or formLogin()
		return httpSecurity.build();
	}

}
