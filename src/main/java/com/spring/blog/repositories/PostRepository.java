package com.spring.blog.repositories;

import com.spring.blog.entities.Category;
import com.spring.blog.entities.Post;
import com.spring.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keywords% OR p.description LIKE %:keywords%")
    Page<Post> findByTitleContainingOrDescriptionContaining(String keywords, Pageable pageable);
}
