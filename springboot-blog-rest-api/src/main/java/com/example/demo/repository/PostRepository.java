package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Post;


public interface PostRepository  extends JpaRepository<Post , Long>{

	
	//we get All the curd  methods by extending the jpa repository

}
