package com.nsh.blog_rest_service.service;

import com.nsh.blog_rest_service.entity.Like;

public interface LikeService {
    boolean createLike(Like like);
    boolean unLike(Like like);
    boolean disLike(Like like);
boolean removeDislike(Like like);
}
