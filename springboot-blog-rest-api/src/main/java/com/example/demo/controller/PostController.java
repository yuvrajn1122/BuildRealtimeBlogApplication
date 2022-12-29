package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.PostDto;
import com.example.demo.payload.PostDtov2;
import com.example.demo.payload.PostResponse;
import com.example.demo.service.PostService;
import com.example.demo.utils.AppConstant;

import jakarta.validation.Valid;

@RestController

public class PostController {

	// to make a loose coupling injecting the interface not the class

	@Autowired
	private PostService postService;
	
	// PostDto createpost (PostDto postDto);=> method of postService
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/v1/posts")
	
	public ResponseEntity<PostDto> createPost( @Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createpost(postDto), HttpStatus.CREATED);

	}

	                 //<============================================= get All post from Api =====================>
	

	//@PreAuthorize( "hasRole('USER')")
	@GetMapping("/api/v1/posts/")
	
	public PostResponse getAllpost( 
			 @RequestParam (value="pageNo",defaultValue=AppConstant.DEFAULT_PAGE_NUMBER,required=false) int pageNo,
			
			@RequestParam(value="pageSize",defaultValue=AppConstant.DEFAULT_PAGE_SIZE,required= false) int pageSize,
			
			@RequestParam(value="sortBy",defaultValue=AppConstant.DEFAULT_SORT_BY,required= false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstant.DEFAULT_SORT_DIRECTION,required= false) String sortDir)
	{
		return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);

	}

	                        // <==================================gatById post================================>


	
	@GetMapping(value={"/api/posts/{id}"},/*params = "version=1"*/ headers="X-API-VERSION=1")
	public ResponseEntity <PostDto >getPostByIdV1(@PathVariable(name = "id") long id) {
		return ResponseEntity.ok( postService.getPostById(id));

	}

	//adding (tags) to the post versioning rest API
	@GetMapping(value={"/api/posts/{id}"},/*params="version=2"*/ headers="X-API-VERSION=2")
	public ResponseEntity<PostDtov2> getPostByIdV2(@PathVariable(name = "id") long id) {
		
		PostDto postDto =postService.getPostById(id);
		
		PostDtov2 postDtov2=new PostDtov2();
		
		postDtov2.setId(postDto.getId());
		postDtov2.setTitle(postDto.getTitle());
		postDtov2.setDescription(postDto.getDescription());
		postDtov2.setContent(postDto.getContent());
		List<String> tags=new ArrayList<>();
		
		
		tags.add("java");
		tags.add("Spring Boot");
		 tags.add("AWS");
		 postDtov2.setTags(tags);
		return ResponseEntity.ok( postDtov2);

	}
	                 // <==================================update post================================>


	
	@PutMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid  @RequestBody PostDto postDto ,@PathVariable long id ) {
		
		PostDto update =postService.updatePost(postDto,id);
		
		return new  ResponseEntity<>(update,HttpStatus.ACCEPTED) ;	
		
	}
	
	
	             // <==================================delete post================================>
	

	
	@DeleteMapping("/api/v1/posts/{id}")
	public ResponseEntity<String> deletePostById( @PathVariable long id){
		
		postService.deletePostById( id); 
		
		return new  ResponseEntity<>("Deleted SucessFull",HttpStatus.OK) ;
		
	}
	}
