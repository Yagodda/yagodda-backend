package com.yagodda.services;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String upload(MultipartFile file) throws IOException;

    ResponseEntity<Resource> show(String uuid) throws IOException;
}
