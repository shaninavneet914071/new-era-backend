package com.nsh.blog_rest_service.service;

import com.nsh.blog_rest_service.payload.PostDto;
import com.nsh.blog_rest_service.payload.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface PostService {
    PostDto createPost(String path, MultipartFile image, PostDto postDto, UUID categoryId) throws IOException;

    PostDto update(PostDto postDto, UUID postId);

    void getPost(UUID postId);

    PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir);
}
