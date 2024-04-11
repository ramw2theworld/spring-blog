package com.spring.blog.services.impl;

import com.spring.blog.entities.Category;
import com.spring.blog.exceptions.ResourceNotFoundException;
import com.spring.blog.payloads.CategoryDto;
import com.spring.blog.repositories.CategoryRepository;
import com.spring.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createNewCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category saved = this.categoryRepository.save(category);

        return this.categoryToDto(saved);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow((()->new ResourceNotFoundException("category", "categoryId", id)));

        if(categoryDto.getTitle() !=null && !categoryDto.getTitle().isEmpty()){
            category.setTitle(categoryDto.getTitle());
        }
        if(categoryDto.getDescription() !=null && !categoryDto.getDescription().isEmpty()){
            category.setDescription(categoryDto.getDescription());
        }

        Category cat = this.categoryRepository.save(category);
        CategoryDto catDto = this.categoryToDto(cat);

        return catDto;
    }

    @Override
    public CategoryDto showCategory(Integer id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow((()->new ResourceNotFoundException("category", "categoryId", id)));
        CategoryDto categoryDto = this.categoryToDto(category);
        return categoryDto;
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow((()->new ResourceNotFoundException("category", "categoryId", id)));

        this.categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDto> fetchAllCategories() {
        List<Category> categories =  this.categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(this::categoryToDto).toList();
        return categoryDtos;
    }

    public CategoryDto categoryToDto(Category category){
        return this.modelMapper.map(category, CategoryDto.class);
    }

    public Category dtoToCategory(CategoryDto categoryDto){
        return this.modelMapper.map(categoryDto, Category.class);
    }

}
