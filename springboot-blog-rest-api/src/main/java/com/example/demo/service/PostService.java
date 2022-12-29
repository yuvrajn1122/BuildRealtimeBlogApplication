package com.example.demo.service;



import com.example.demo.payload.PostDto;
import com.example.demo.payload.PostResponse;

public interface PostService {

	PostDto createpost(PostDto postDto);

	
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	
	PostDto getPostById(long id);

	
	
	PostDto updatePost(PostDto postDto, long id);

	
	
	void deletePostById(long id);








}
