package com.nsh.blog_rest_service.service.impl;

import com.nsh.blog_rest_service.entity.Category;
import com.nsh.blog_rest_service.exception.ResourceNotFoundException;
import com.nsh.blog_rest_service.payload.CategoryDto;
import com.nsh.blog_rest_service.repository.CategoryRepo;
import com.nsh.blog_rest_service.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ModelMapper modelMapper;

    /**
     * @param categoryDto
     * @return
     */
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category addedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(addedCategory, CategoryDto.class);
    }

    /**
     * @param categoryDto
     * @param id
     * @return
     */
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, UUID id) {
        Category cat = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", String.valueOf(id)));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }

    /**
     * @param id
     */
    @Override
    public void deleteCategory(UUID id) {
        Category cat = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", String.valueOf(id)));
        this.categoryRepo.delete(cat);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CategoryDto getCategory(UUID id) {
        Category cat = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", String.valueOf(id)));
        return this.modelMapper.map(cat, CategoryDto.class);
    }

    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> catDtos = categories.stream()
                .map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return catDtos;
    }
}
