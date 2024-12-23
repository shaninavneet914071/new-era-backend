package com.nsh.blog_rest_service.service;

import com.nsh.blog_rest_service.payload.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, UUID id);

    void deleteCategory(UUID id);

    CategoryDto getCategory(UUID id);

    List<CategoryDto> getAllCategory();

}
