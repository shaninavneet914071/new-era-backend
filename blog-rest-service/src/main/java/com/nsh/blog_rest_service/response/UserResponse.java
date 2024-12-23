package com.nsh.blog_rest_service.response;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
    private String role;
    private String about;
}
