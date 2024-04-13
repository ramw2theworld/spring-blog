package com.spring.blog.services.impl;

import com.spring.blog.entities.Category;
import com.spring.blog.entities.Post;
import com.spring.blog.entities.User;
import com.spring.blog.exceptions.ResourceNotFoundException;
import com.spring.blog.payloads.CategoryDto;
import com.spring.blog.payloads.PostDto;
import com.spring.blog.payloads.UserDto;
import com.spring.blog.repositories.CategoryRepository;
import com.spring.blog.repositories.PostRepository;
import com.spring.blog.repositories.UserRepository;
import com.spring.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createNewPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "user_id", userId));

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "category_id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setUser(user);
        post.setCategory(category);
        post.setSlug(slugify(postDto.getTitle()));
        post.setImage("default.png");

        if (post.getPublishedAt() == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            post.setPublishedAt(dateFormat.format(new Date()));
        }

        Post newPost = postRepository.save(post);
        return modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id) {
        Post post = this.postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "post_id", id));
        post.setTitle(postDto.getTitle());
        post.setSlug(this.slugify(postDto.getTitle()));
        post.setDescription(postDto.getDescription());
        Post savedPost = this.postRepository.save(post);
        PostDto postDto1=this.modelMapper.map(savedPost, PostDto.class);

        return postDto1;
    }

    @Override
    public PostDto showPost(Integer id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post", "post_id", id));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post", "post_id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> fetchAllPosts() {
        List<Post> posts = this.postRepository.findAll();
        List<PostDto> postDtos =  posts.stream().map(this::postToDto).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "user_id", userId));
        List<Post> posts = this.postRepository.findByUser(user);
        List<PostDto> postDtos = posts.stream().map(this::postToDto).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "category_id", categoryId));
        List<Post> posts = this.postRepository.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map(this::postToDto).collect(Collectors.toList());
        return postDtos;
    }

    public PostDto postToDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
    public Post dtoToPost(PostDto postDto){
        return this.modelMapper.map(postDto, Post.class);
    }
    private String slugify(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "") // Remove special characters except spaces
                .trim() // Remove leading and trailing spaces
                .replaceAll("\\s+", "-"); // Replace spaces with hyphens
    }
}
