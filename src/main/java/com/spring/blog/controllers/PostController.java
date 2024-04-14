package com.spring.blog.controllers;

import com.spring.blog.configs.AppConstants;
import com.spring.blog.payloads.ApiResponse;
import com.spring.blog.payloads.PostDto;
import com.spring.blog.payloads.PostResponse;
import com.spring.blog.services.FileService;
import com.spring.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;

    @PostMapping("/posts/{userId}/{categoryId}")
    public ResponseEntity<PostDto> createNewPost(@RequestBody PostDto postDto, @PathVariable int userId, @PathVariable int categoryId){
        PostDto newPostDto = this.postService.createNewPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(newPostDto, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> fetchAllPosts(
            @RequestParam(value = "page-number", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "page-size", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sort-by", defaultValue = AppConstants.SORT_BY, required = false) String sort_by,
            @RequestParam(value = "sort-dir", defaultValue = AppConstants.SORT_DIR, required = false) String sort_dir,
            @RequestParam(value = "search-keyword", defaultValue = "", required = false) String search_keyword
    ){
        PostResponse postResponse = this.postService.fetchAllPosts(pageNumber, pageSize, sort_by, sort_dir, search_keyword);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/posts-by-category/{categoryId}")
    public ResponseEntity<List<PostDto>> fetchAllPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> posts = this.postService.getAllPostByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts-by-user/{userId}")
    public ResponseEntity<List<PostDto>> fetchPostsByUser(@PathVariable Integer userId){
        List<PostDto> posts = this.postService.getAllPostByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> showPost(@PathVariable Integer postId){
        PostDto postDto = this.postService.showPost(postId);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto postDto1= this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(postDto1, HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/image-upload")
    public ResponseEntity<PostDto> uploadImageFile(@PathVariable Integer postId, @RequestBody MultipartFile image) throws IOException {
        PostDto fileUploaded = this.postService.uploadImage(postId, image);
        return new ResponseEntity<PostDto>(fileUploaded, HttpStatus.OK);
    }
}
