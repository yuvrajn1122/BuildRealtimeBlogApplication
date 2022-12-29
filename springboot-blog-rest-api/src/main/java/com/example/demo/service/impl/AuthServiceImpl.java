package com.example.demo.service.impl;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Security.JwtTokanProvider;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.BlogAPIException;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
 
	
	
	    @Autowired
		private AuthenticationManager authenticationManager;
	
	    @Autowired
	     private UserRepository userRepository;	
	    
	    @Autowired
	    private RoleRepository roleRepository;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private JwtTokanProvider jwtTokanProvider;
	
	

	@Override
	public String login(LoginDto loginDto) {
		Authentication authentication= authenticationManager.authenticate
				
				(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), 
						loginDto.getPassword()));
		
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token =jwtTokanProvider.generateToken(authentication);
		return  token;//" User have logged in successfully!.....";
	}



	@Override
	public String register(RegisterDto registerDto) {
		
		
		//add check for username exist in database
		if(userRepository.existsByUsername( registerDto.getUsername())){
			
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username  is already exist!..");
			
		}
		
		//add check for email  exist in database
		
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			 
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"email  is already exist!..");
		}
		
		User user=new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword( passwordEncoder.encode( registerDto.getPassword()));
		
		
		Set<Role> roles=new HashSet<>();
		
		Role userRole=roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		
		user.setRoles(roles);
		
		userRepository.save(user);
		
		
		
		return "User Registered successfully!..";
	}

}
