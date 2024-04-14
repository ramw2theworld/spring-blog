package com.spring.blog.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDto {
    private int id;
    private String title;
    private String slug;
    private String image;
    private Date publishedAt;
    private String description;

    private UserDto user;
    private CategoryDto category;

    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", image='" + image + '\'' +
                ", publishedAt=" + publishedAt +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}
