package com.nsh.blog_rest_service.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {
    private String fileName;
    private String message;

    public FileResponse(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }

}
