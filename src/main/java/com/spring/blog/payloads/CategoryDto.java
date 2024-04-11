package com.spring.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer id;

    @NotEmpty(message = "Category title is required")
    @Size(min = 2, message = "Category name should have at least 2 characters")
    private String title;
    @Size(max = 500, message = "Description should not be greater than 500 characters")
    private String description;
}
