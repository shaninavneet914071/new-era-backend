package com.nsh.blog_rest_service.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@Builder
public class ResponseEntity<T> {
    private HttpStatus status;
    private String message;
    private T body;

}
