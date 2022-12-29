package com.example.demo.service.impl;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.exception.BlogAPIException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.CommentDto;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.CommentService;

@Service
public class CommentServiceImpl  implements CommentService{

	@Autowired
    private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	 
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		
		
		 Comment  comment= mapToEntity(commentDto);
		 
		 //Retrive post Entity By Id
		 Post post=postRepository.findById(postId).orElseThrow(
				 ()-> new ResourceNotFoundException("Post","id",postId));
		  
		 //Set Post To Comment Entity
		 comment.setPost(post);
		 
		 //Save CommentEntity To DataBase
		 
		Comment newComment= commentRepository.save(comment);
		
		
		return mapToDto(newComment);
	}
	
	
	//get CommentDto by id
	
	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		 List<Comment> comments = commentRepository.findByPostId(postId);

	        // convert list of comment entities to list of comment dto's
	        return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
		
	}
	
	
	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		
		//Retrieve post from the database by id
		
		Post post=postRepository.findById(postId).orElseThrow(
				 ()-> new ResourceNotFoundException("Post","id",postId));
		
		//Retrieve comment from the database
		
		Comment comment=commentRepository.findById(commentId).orElseThrow(
				()-> new ResourceNotFoundException("Comment","id",commentId) );
		
		// if their is the exception pass through it
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
		}
		return  mapToDto(comment);
	}
	
	
	//update Comment
	
	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
		//Retrieve post from the database by id
		Post post=postRepository.findById(postId).orElseThrow(
				 ()-> new ResourceNotFoundException("Post","id",postId));
		
		//Retrieve comment from the database
		
				Comment comment=commentRepository.findById(commentId).orElseThrow(
						()-> new ResourceNotFoundException("Comment","id",commentId) );
				
				
				if(!comment.getPost().getId().equals(post.getId())) {
					throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
				}
				
				comment.setName(commentRequest.getName());
				comment.setEmail(commentRequest.getEmail());
				comment.setBody(commentRequest.getBody());
				
				Comment newComment=commentRepository.save(comment);
				
				
		return mapToDto(newComment) ;
	}
	
	
	//delete id
	@Override
	public void deleteByID(Long postId, Long commentId) {
		
		//Retrieve post from the database by id
				Post post=postRepository.findById(postId).orElseThrow(
						 ()-> new ResourceNotFoundException("Post","id",postId));
				
				//Retrieve comment from the database
				
						Comment comment=commentRepository.findById(commentId).orElseThrow(
								()-> new ResourceNotFoundException("Comment","id",commentId) );
						
						//exception 
						
						if(!comment.getPost().getId().equals(post.getId())) {
							throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
						}
						
						commentRepository.delete(comment);
						
						
		
	}
	
	
	
	
	                                                                                                        //creating CommentEntity to CommentDto
	
	private CommentDto mapToDto( Comment comment) {
		
		
		CommentDto commentDto=modelMapper.map(comment, CommentDto.class);
		return commentDto;
		
	}
	
	//creating  CommentDto to CommentEntity
	
	private Comment mapToEntity( CommentDto  commentDto) {
		
		Comment comment= modelMapper.map(commentDto, Comment.class);
		 
		return comment;
		
	}

/*
 
  
  CommentDto commentDto=new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());
		
		
		
		
		 Comment  comment=new  Comment();
		 comment.setId( commentDto.getId());
		 comment.setName(commentDto.getName());
		 comment.setEmail(commentDto.getEmail());
		 comment.setBody(commentDto.getBody());
		 
 */

	



	



	

}
