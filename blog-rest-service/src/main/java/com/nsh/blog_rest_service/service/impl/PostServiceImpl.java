package com.nsh.blog_rest_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsh.blog_rest_service.entity.Category;
import com.nsh.blog_rest_service.entity.Post;
import com.nsh.blog_rest_service.exception.ResourceNotFoundException;
import com.nsh.blog_rest_service.payload.CategoryDto;
import com.nsh.blog_rest_service.payload.PostDto;
import com.nsh.blog_rest_service.payload.PostResponse;
import com.nsh.blog_rest_service.repository.PostRepo;
import com.nsh.blog_rest_service.service.CategoryService;
import com.nsh.blog_rest_service.service.FileService;
import com.nsh.blog_rest_service.service.PostService;
import com.nsh.blog_rest_service.service.UserService;
import com.nsh.blog_rest_service.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final ModelMapper modelMapper;

    private final UserService userRepo;

    private final CategoryService categoryRepo;
    private final ObjectMapper mapper;
private final FileService fileService;
private  final HttpServletRequest request;

    public PostServiceImpl(PostRepo postRepo, ModelMapper modelMapper, UserService userRepo, CategoryService categoryRepo, ObjectMapper mapper, FileService fileService, HttpServletRequest request) {
        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
        this.fileService = fileService;
        this.request = request;
    }
    /**
     * @param postDto
     * @return
     */
//    @Override
//    public PostDto createPost(String postDto, Integer userId, Integer categoryId) {
//
//        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "usrid", userId));
//        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", categoryId));
//
//        Post post = this.modelMapper.map(postDto, Post.class);
//        post.setImageName("default.png");
//        post.setAddedDate(new Date());
//        post.setUser(user);
//        post.setCategory(category);
//
//        Post newPost = this.postRepo.save(post);
//        return this.modelMapper.map(newPost, PostDto.class);
//    }
    /**
     * @param path
     * @param image
     * @param postDto
     * @param categoryId
     * @return
     */
    @Override
    public PostDto createPost(String path, MultipartFile image, PostDto postDto, UUID categoryId) throws IOException {
        UUID userId = TokenUtil.getUserIdFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        CategoryDto categorydto = this.categoryRepo.getCategory(categoryId);
        Category catData = this.modelMapper.map(categorydto,Category.class);

        String file=this.fileService.uploadImage(path, image);
        postDto.setUserId(userId);
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setAddedDate(new Date());
        post.setCategories(List.of(catData));
            post.setImageName(file);

        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    /**
     * @param postDto
     * @param postId
     * @return
     */
    @Override
    public PostDto update(PostDto postDto, UUID postId) {
UUID userId = TokenUtil.getUserIdFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Post post = this.postRepo.findByUserIdAndPostId(userId,postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postID", String.valueOf(postId)));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    /**
     * @param postId
     */
    @Override
    public void getPost(UUID postId) {
UUID userId = TokenUtil.getUserIdFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Post post = this.postRepo.findByUserIdAndPostId(userId,postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", String.valueOf(postId)));
        this.postRepo.delete(post);
    }

    /**
     * @return
     */
    @Override
    public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) {
UUID userId = TokenUtil.getUserIdFromToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePosts = this.postRepo.findAllByUserId(userId,pageable);
        List<Post> allPosts = pagePosts.getContent();
        List<PostDto> postDtos = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }



    /**
     * @param categoryId
     * @return
     */
//    @Override
//    public List<PostDto> getPostsByCategory(UUID categoryId) {
//        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", String.valueOf(categoryId)));
//        List<Post> posts = this.postRepo.findByCategory(category);
//        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//        return postDtos;
//    }

    /**
     * @param userId
     * @return
     */
//    @Override
//    public List<PostDto> getPostsByUser(Integer userId) {
////        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
////        List<Post> posts = this.postRepo.findByUser(user);
////        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
////        return postDtos;
//        return null;
//    }

//    /**
//     * @param keyword
//     * @return
//     */
//    @Override
//    public List<PostDto> searchPosts(String keyword) {
//        List<Post> posts=this.postRepo.findByTitleContaining(keyword);
//        List<PostDto> postDtos=posts.stream().map(post -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
//        return postDtos;
//    }

    /**
     * @param keyword
     * @return
     */
//    @Override
//    public List<PostDto> searchPosts(String keyword) {
//        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
//        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//        return postDtos;
//    }



}
