package com.nsh.blog_rest_service.service.impl;

import com.nsh.blog_rest_service.entity.User;
import com.nsh.blog_rest_service.exception.ApiException;
import com.nsh.blog_rest_service.exception.ResourceNotFoundException;
import com.nsh.blog_rest_service.feignclient.CustomerFeignClient;
import com.nsh.blog_rest_service.feignclient.response.CustomerDto;
import com.nsh.blog_rest_service.feignclient.response.Role;
import com.nsh.blog_rest_service.payload.UserDto;
import com.nsh.blog_rest_service.repository.UserRepo;
import com.nsh.blog_rest_service.response.UserResponse;
import com.nsh.blog_rest_service.service.UserService;
import com.nsh.blog_rest_service.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    private final HttpServletRequest request;
    private final CustomerFeignClient customerClient;

    public UserServiceImpl(HttpServletRequest request, CustomerFeignClient customerClient) {
        this.request = request;
        this.customerClient = customerClient;
    }

    /**
     * @param userDto
     * @return
     */
    @Override
    public UserResponse createUser(UserDto userDto) {
        UUID userId = TokenUtil.getUserIdFromToken(TokenUtil.parseHeaderToken(request.getHeader(HttpHeaders.AUTHORIZATION)));
        CustomerDto customerData = customerClient.find(null,userId, TokenUtil.parseHeaderToken(request.getHeader(HttpHeaders.AUTHORIZATION))).getBody();

        if(Objects.isNull(customerData) || customerData.getRole() == null || customerData.getEmail() == null){
            throw new ResourceNotFoundException("UserData","user id",String.valueOf(userId));
        }
        if(getUserById(userId) == null || getUserByEmail(customerData.getEmail()) == null){
             throw new ApiException("User Already Exist!");
        }
        Role role = customerData.getRole();
        User user = this.dtoToUser(userDto);
        user.setUserType(role.name());
        user.setUserId(String.valueOf(userId));
        user.setEmail(customerData.getEmail());
        user.setName(customerData.getFirstName() +" "+customerData.getLastName());
        User savedUser = this.userRepo.save(user);
        return this.userToUserResponse(savedUser);
    }

    /**
     * @param userId
     * @return
     */

    private User getUserById(UUID userId) {
        return this.userRepo.findByUserId(String.valueOf(userId)).orElse(null);
    }

    private User getUserByEmail(String email) {
        return this.userRepo.findByEmail(email).orElse(null);
    }

    /**
     * @param id
     */
    @Override
    public void deleteUser(UUID id) {
         this.userRepo.deleteById(id);
    }



    public User dtoToUser(UserDto userDto) {
        return User.builder().bloggerName(userDto.getBloggerName()).about(userDto.getAbout()).build();
    }

    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).role(user.getUserType()).about(user.getAbout()).build();
    }

}
