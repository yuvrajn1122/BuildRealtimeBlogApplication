package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder  {
	public static void main(String[] args) {
		 PasswordEncoder encoder=new BCryptPasswordEncoder();
		 System.out.println(encoder.encode("yuvi1122"));
		 System.out.println(encoder.encode("admin123"));
	}

}
