package com.example.demo.payload;

import java.util.List;
import java.util.Set;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDtov2 {
	
private long id;
	
	@NotEmpty
	@NotNull
	@Size( min=2,message="Post Titel Should have atlist 2 char & it must be Not null")
	private String title ;
	
	@NotEmpty
	@NotNull
	@Size( min=10,message="Post description Should have atlist 10 char & it must be Not null")
	
	private String description;
	
	@NotEmpty
	private String content;
	
	private Set<CommentDto> comments;
	
	private List<String> tags;

}
