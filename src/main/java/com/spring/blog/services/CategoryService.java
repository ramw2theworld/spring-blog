package com.spring.blog.services;

import com.spring.blog.payloads.CategoryDto;
import com.spring.blog.payloads.UserDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createNewCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer id);
    CategoryDto showCategory(Integer id);
    void deleteCategory(Integer id);
    List<CategoryDto> fetchAllCategories();
}
