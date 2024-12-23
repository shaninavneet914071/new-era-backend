package com.nsh.blog_rest_service.repository;

import com.nsh.blog_rest_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
}
