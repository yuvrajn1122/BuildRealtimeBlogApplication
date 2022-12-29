package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.JwtAuthResponse;
import com.example.demo.payload.LoginDto;
import com.example.demo.payload.RegisterDto;
import com.example.demo.service.AuthService;


@RestController

//basr url
@RequestMapping("/api/auth/")
public class AuthController {
	
	
	private AuthService authService;
	
    public AuthController(AuthService authService) {
		
		this.authService = authService;
	}


	//build ligin Rest Api
	@PostMapping(value={"/login","/signin"})
	//build login Rest Api
	public ResponseEntity<JwtAuthResponse> login (@RequestBody LoginDto loginDto){
		
			String token=	authService.login(loginDto);
			
			JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
			
			jwtAuthResponse.setAccessTokan(token);
			return ResponseEntity.ok(jwtAuthResponse);
		
		
	}
	
	
	//Build Register RestApi
	@PostMapping(value ={"/register","/signup"})
	ResponseEntity<String> register( @RequestBody RegisterDto registerDto){
		
		String response=authService.register(registerDto);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	
	
	
	
	
	
	
	
	

    
    
}
