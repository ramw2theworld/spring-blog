package com.spring.blog.services;

import com.spring.blog.entities.Post;
import com.spring.blog.payloads.PostDto;

import java.util.List;

public interface PostService {
    PostDto createNewPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer id);
    PostDto showPost(Integer id);
    void deletePost(Integer id);
    List<PostDto> fetchAllPosts();
    List<PostDto> getAllPostByUser(Integer userId);
    List<PostDto> getAllPostByCategory(Integer categoryId);
}
