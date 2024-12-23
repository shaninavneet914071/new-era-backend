package com.nsh.blog_rest_service.repository;

import com.nsh.blog_rest_service.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostRepo extends JpaRepository<Post, UUID> {
    Optional<Post> findByUserIdAndPostId(UUID userId, UUID postId);
    Page<Post> findAllByUserId(UUID userId, Pageable pageable);
//    List<Post> findByUser(User user);
//
//    List<Post> findByCategory(Category category);
//
//    List<Post> findByTitleContaining(String title);

}
