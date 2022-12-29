package com.example.demo.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

	// we are not Going To Expose Directly comment Entity to the client to the
	// response of the Api

	
	private long id;

	 
	@NotEmpty(message = "Name Should Not be Null Or Empty")

	
	private String name;

	
	
	@NotEmpty(message = "Email Should Not be Null Or Empty")
	@Email
	private String email;

	
	
	@NotEmpty
	@Size(min = 10, message = "CommentBody must be minimum 10 character")
	private String body;

}
