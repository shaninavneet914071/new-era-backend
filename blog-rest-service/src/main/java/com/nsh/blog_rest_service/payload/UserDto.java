package com.nsh.blog_rest_service.payload;

import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {
    @Size(min = 4, max = 20, message = "Name size must be between 4-20 chars")
    private String bloggerName;
    private String about;
}
