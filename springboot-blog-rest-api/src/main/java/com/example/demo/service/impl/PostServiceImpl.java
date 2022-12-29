package com.example.demo.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.demo.entity.Post;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.PostDto;
import com.example.demo.payload.PostResponse;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	
	public PostDto createpost(PostDto postDto) {

		
		Post post=mapToPostEntity(postDto);
				
		Post newPost=postRepository.save(post);
		                                                                                                   //newPost is the reference obj of postRepository
		                                                                                              //convert entity to dto
		PostDto postResponse= mapToDto(newPost);
		                                                                                        		//creating the object of PostDto 
		 
		return postResponse;
	}
          //<===========================================findAll method======================================>
	
	@Override
	public PostResponse getAllPosts( int pageNo ,int pageSize,String sortBy,String sortDir) {
		
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()        //this lis is to impliment the Asc order 
				:Sort.by(sortBy).descending();
					
		                                                                                                // created pageable instance
		
		Pageable pageable=PageRequest.of(pageNo, pageSize,sort);                                       //page request is used overloded of method
		
		Page<Post> posts=postRepository.findAll(pageable);
		
		                                                                                           //get content for page object to retrive the list content/page obj
		List<Post> listOfPosts=posts.getContent();
		
		
		
		
		List<PostDto>response = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());//hear we convart list of post in to postDtos

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(response);
		postResponse.setPageNo(pageNo);
		postResponse.setPageSize(pageSize);
		postResponse.setTotalElements(pageSize);
		postResponse.setTotalPages(pageSize);
		postResponse.setLast(posts.isLast());
		
		return postResponse;
		
		
		
	
	}
	
	
	                      //  <------------------------getPostByID------------------------------------>
	
	@Override
	public PostDto getPostById(long id) {
		Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
		return mapToDto(post);
	}

	
	
	                     // <------------------------------updatePost----------------------------------->
	
	
	@Override
	
	public PostDto updatePost(PostDto postDto, long id) {
		
		Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post update= postRepository.save(post);
		
		return mapToDto(update);
	}
	
	
	                  // <------------------------------deletePost----------------------------------->
	
	
	
	@Override
	public void  deletePostById( long id) {
		Post post=postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
		
		
		postRepository.delete(post);
		
	}
	
	
	
	
	private PostDto mapToDto(Post post) {
		
		PostDto postDto=modelMapper.map(post, PostDto.class);
			
		return postDto;
		
	}
	
	
	private Post mapToPostEntity(PostDto postDto) {		
		
		Post post =modelMapper.map(postDto, Post.class);
		return post;
		
	}

	

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//convert post entity to postDto
	
	/*
	PostDto postDto=new PostDto();
	
	postDto.setId(post.getId());
	
	postDto.setTitle(post.getTitle());
	
	postDto.setDescription(post.getDescription());
	
	 postDto.setContent(post.getContent());
	 
	 
	 
	 
	 //convert post Dto to entity
	  * Post post = new Post();
		// convert dto to entity
		//hear post is the entity class
		post.setId(postDto.getId());
		
		post.setTitle(postDto.getTitle());//hear we are gatting the tital from 
		//postDto and seting in post
		
		post.setDescription(postDto.getDescription());
		
		post.setContent(postDto.getContent());
	*/
	
	
	

	
	

}
