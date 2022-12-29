package com.example.demo.service;

import java.util.List;


import com.example.demo.payload.CommentDto;


public interface CommentService  {
	
	CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);
    
    

    CommentDto getCommentById(Long postId,Long commentId);
    
    
    CommentDto updateComment(Long postId,Long commentId,CommentDto commentRequest);
    
    
    
    
   void deleteByID(Long postId,Long commentId);
    

	
	

}
