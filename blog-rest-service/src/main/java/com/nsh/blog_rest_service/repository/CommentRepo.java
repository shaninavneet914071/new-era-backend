package com.nsh.blog_rest_service.repository;

import com.nsh.blog_rest_service.entity.Comment;
import com.nsh.blog_rest_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepo extends JpaRepository<Comment, UUID> {
    List<Comment> findByPost(Post post);
}
