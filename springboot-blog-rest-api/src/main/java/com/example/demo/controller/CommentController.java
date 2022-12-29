package com.example.demo.controller;

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
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.payload.CommentDto;
import com.example.demo.service.CommentService;

import jakarta.validation.Valid;

@RestController

@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	
	
	@PostMapping("/posts/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId")long postId,
			                                        @Valid @RequestBody CommentDto commentDto){
		return  new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED) ;
		
	}
	
	
	
	
	@GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }
	
	
	
	
	
	@GetMapping("/posts/{postId}/comments/{comid}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,
			                                         @PathVariable (value = "comid")  Long commentId) {
		
		
		return new ResponseEntity<>(commentService.getCommentById(postId, commentId),HttpStatus.OK);
		
	}
	
	//update comment by id
	
	@PutMapping("/posts/{postId}/comments/{comid}")
	public ResponseEntity<CommentDto> updateComment( @PathVariable (value="postId")Long postId,
		                                            	@PathVariable (value = "comid") Long commentId,
		                                            	@Valid   @RequestBody CommentDto commentDto){
		
		
		CommentDto updateComment=commentService.updateComment(postId, commentId, commentDto);
		
		
		return new ResponseEntity<>(updateComment,HttpStatus.OK);
		
	}
	
	//delete comment
	
	
	@DeleteMapping("/posts/{postId}/comments/{comid}")
	public  ResponseEntity<String> deleteByID(@PathVariable (value="postId")Long postId,
        	                                   @PathVariable (value = "comid") Long commentId) {
		commentService.deleteByID(postId, commentId);
		
		
		return  new  ResponseEntity<>(" Comment Deleted Successfully",HttpStatus.OK);	
		
	}
	
	
	
}
