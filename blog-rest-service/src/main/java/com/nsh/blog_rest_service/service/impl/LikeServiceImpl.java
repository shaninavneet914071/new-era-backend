package com.nsh.blog_rest_service.service.impl;

import com.nsh.blog_rest_service.entity.Like;
import com.nsh.blog_rest_service.repository.LikeRepo;
import com.nsh.blog_rest_service.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;

public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepo likeRepo;

    @Override
    public boolean createLike(Like like) {
        Like like1 = likeRepo.save(like);
        return like1.isLike();
    }

    @Override
    public boolean unLike(Like like) {
        return false;
    }

    @Override
    public boolean disLike(Like like) {
        return false;
    }

    @Override
    public boolean removeDislike(Like like) {
        return false;
    }
}
