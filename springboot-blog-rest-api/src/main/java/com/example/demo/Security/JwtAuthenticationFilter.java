package com.example.demo.Security;

import java.io.IOException;

import org.apache.catalina.startup.WebAnnotationSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtTokanProvider jwtTokanProvider;
	@Autowired
	private  UserDetailsService userdetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			                        HttpServletResponse response,
			                        FilterChain filterChain)
			throws ServletException, IOException {
		//get jwt token from http request
		
		String token=getTokenFromRequest(request);
		
		//validate tokan 
		
		if(StringUtils.hasText(token) &&  jwtTokanProvider.validationToken(token)) {
			
			
			//get user name from token 
			
		String username=jwtTokanProvider.getusername(token);
			
		UserDetails usredetails=userdetailsService.loadUserByUsername(username);
		
		UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(usredetails,null
				                                ,usredetails.getAuthorities());
		
		authenticationToken.setDetails(new WebAuthenticationDetailsSource() .buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getTokenFromRequest(HttpServletRequest httpServletRequest) {
		
		String barerToken=httpServletRequest.getHeader("Authorization");
		
		if(StringUtils.hasText(barerToken) && barerToken.startsWith("Bearer"))
		return  barerToken.substring(7,barerToken.length());
		return null;
		
	}

}
