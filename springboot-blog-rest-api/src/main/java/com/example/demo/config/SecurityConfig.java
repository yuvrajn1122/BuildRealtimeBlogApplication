package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Security.JWTAuthenticationEntrypoint;
import com.example.demo.Security.JwtAuthenticationFilter;

@Configuration

@EnableWebSecurity

@EnableMethodSecurity
public class SecurityConfig {
	
	
     @Autowired
	private UserDetailsService userdetaisService;

	@Autowired
	private JWTAuthenticationEntrypoint  authenticationEntrypoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	
	// create AuthenticationManager in project step 1
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) 
			throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable() // to authorize all the in coming Http request to our Applocation
				// this also take the lambda expretion
				.authorizeHttpRequests((authorize) ->
//			      
				authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
				.requestMatchers("/api/auth/**").permitAll()
				.anyRequest()
				.authenticated()
				)
				
				.exceptionHandling(exception -> exception  
						              .authenticationEntryPoint(authenticationEntrypoint))
				
				                      .sessionManagement(session -> session 
						
						              .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
	              http.addFilterBefore(authenticationFilter,
	                    		
	                    		      UsernamePasswordAuthenticationFilter.class);
	              
	    				 

		return http.build();
	}

	/*
	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails admin = User.builder().username("yuvraj").password(passwordEncoder().encode("yuva")).roles("ADMIN")
				.build();

		UserDetails user = User.builder().username("user").password(passwordEncoder().encode("user")).roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user, admin);

	}
	*/ 

}
