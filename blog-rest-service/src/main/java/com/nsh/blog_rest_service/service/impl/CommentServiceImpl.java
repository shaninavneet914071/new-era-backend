package com.nsh.blog_rest_service.service.impl;

import com.nsh.blog_rest_service.entity.Comment;
import com.nsh.blog_rest_service.entity.Post;
import com.nsh.blog_rest_service.exception.ResourceNotFoundException;
import com.nsh.blog_rest_service.payload.CommentDto;
import com.nsh.blog_rest_service.repository.CategoryRepo;
import com.nsh.blog_rest_service.repository.CommentRepo;
import com.nsh.blog_rest_service.repository.PostRepo;
import com.nsh.blog_rest_service.repository.UserRepo;
import com.nsh.blog_rest_service.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    /**
     * @param commentDto
     * @param postId
     * @param categoryId
     * @param userId
     * @return
     */
    @Autowired
    PostRepo postRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, UUID postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", String.valueOf(postId)));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment comment1 = this.commentRepo.save(comment);
        return this.modelMapper.map(comment1, CommentDto.class);
    }

    /**
     * @param postId
     * @return
     */
    @Override
    public List<CommentDto> getCommentByPostId(UUID postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostId",  String.valueOf(postId)));
        List<Comment> comments = this.commentRepo.findByPost(post);
        List<CommentDto> commentDtos = comments.stream().map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
        return commentDtos;
    }

    public CommentDto getCommentById(UUID commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentID", String.valueOf(commentId)));
        CommentDto commentDtos = this.modelMapper.map(comment, CommentDto.class);
        return commentDtos;

    }

}
