package com.spring.blog.services;

import com.spring.blog.payloads.PostDto;
import com.spring.blog.payloads.PostResponse;
import java.util.List;

public interface PostService {
    PostDto createNewPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer id);
    PostDto showPost(Integer id);
    void deletePost(Integer id);
    PostResponse fetchAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String searchKeyword);
    List<PostDto> getAllPostByUser(Integer userId);
    List<PostDto> getAllPostByCategory(Integer categoryId);
}
