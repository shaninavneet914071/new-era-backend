package com.nsh.blog_rest_service.repository;

import com.nsh.blog_rest_service.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Like,Long> {


}
