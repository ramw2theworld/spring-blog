package com.spring.blog.services;

import com.spring.blog.payloads.CategoryDto;
import com.spring.blog.payloads.UserDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto createNewCategory(CategoryDto categoryDto);
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id);
    public CategoryDto showCategory(Integer id);
    public void deleteCategory(Integer id);
    public List<CategoryDto> fetchAllCategories();
}
