package com.nsh.blog_rest_service.service;

import com.nsh.blog_rest_service.payload.CommentDto;

import java.util.List;
import java.util.UUID;

public interface CommentService {


    CommentDto createComment(CommentDto commentDto, UUID postId);

    List<CommentDto> getCommentByPostId(UUID postId);
}
