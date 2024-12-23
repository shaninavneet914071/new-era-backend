package com.nsh.blog_rest_service.exception;

public class ApiException extends RuntimeException {
    /**
     * @param message
     */
    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super();
    }
}
