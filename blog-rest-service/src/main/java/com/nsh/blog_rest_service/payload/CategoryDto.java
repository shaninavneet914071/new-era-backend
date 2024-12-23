package com.nsh.blog_rest_service.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private UUID categoryId;
    private String categoryTitle;
    private String categoryDescription;
}
