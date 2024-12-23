package com.nsh.blog_rest_service.service;

import com.nsh.blog_rest_service.payload.UserDto;
import com.nsh.blog_rest_service.response.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse createUser(UserDto userDto);
    void deleteUser(UUID userId);
}
