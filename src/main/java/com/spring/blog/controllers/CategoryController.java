package com.spring.blog.controllers;

import com.spring.blog.payloads.ApiResponse;
import com.spring.blog.payloads.CategoryDto;
import com.spring.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createNewCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = this.categoryService.createNewCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("id") int id){
        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryDto, id);
        return ResponseEntity.ok(categoryDto1);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> showCategory(@PathVariable Integer id){
        CategoryDto categoryDto = this.categoryService.showCategory(id);
        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> fetchAllCategories(){
        List<CategoryDto> categoryDtos = this.categoryService.fetchAllCategories();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }
}
