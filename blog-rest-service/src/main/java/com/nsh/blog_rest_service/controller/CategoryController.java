package com.nsh.blog_rest_service.controller;

import com.nsh.blog_rest_service.feignclient.CustomerFeignClient;
import com.nsh.blog_rest_service.payload.CategoryDto;
import com.nsh.blog_rest_service.payload.ResponseEntity;
import com.nsh.blog_rest_service.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
   private final CustomerFeignClient customerClient;

    public CategoryController(CustomerFeignClient customerClient) {
        this.customerClient = customerClient;
    }


    @PostMapping("/addCategory")
    @Operation(summary = "Get Hello Message", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto category = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(HttpStatus.CREATED,"Success",category);
    }
    @PutMapping("/updateCategory")
    @Operation(summary = "Get Hello Message", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, UUID categoryId) {
        CategoryDto category = this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(HttpStatus.OK,"Success",category);
    }
    @GetMapping("/get-Category")
    @Operation(summary = "Get Hello Message", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<CategoryDto> getCategory(UUID categoryId) {
        CategoryDto category = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK,"Success",category);
    }


    @DeleteMapping("/deleteCategory")
    @Operation(summary = "Get Hello Message", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<Void> deleteCategory(UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK, "Success", null);
    }
    @PostMapping("get-all-categories")
    @Operation(summary = "Get Hello Message", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    public ResponseEntity<List<CategoryDto>> fetchCategories() {
        List<CategoryDto> categories = this.categoryService.getAllCategory();
        return new ResponseEntity<>(HttpStatus.OK, "Success", categories);
    }
}
