package com.spring.blog.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length=299)
    private String title;

    @Column(nullable = false, length = 600)
    private String slug;

    @Column(length = 10000, nullable = true)
    private String description;

    private String image;

    @Column(name = "published_at", nullable = true)
    private String publishedAt;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", category=" + category +
                ", user=" + user +
                '}';
    }
}
