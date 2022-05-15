package com.yagodda.controllers;

import com.yagodda.models.FilePath;
import com.yagodda.payload.response.MessageResponse;
import com.yagodda.services.FileStorageService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files/")
@RequiredArgsConstructor
@Slf4j
public class FileStorageController {
    private final FileStorageService fileStorageService;

    @ApiOperation("Upload file in folder")
    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> upload(@RequestParam("file") MultipartFile file) {
        String fileUuid;

        try {
            fileUuid = fileStorageService.upload(file);
        } catch (IOException e) {
            log.debug("Can't upload file: {}", e.getMessage());
            return new ResponseEntity<>(new MessageResponse("File upload error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(new MessageResponse(fileUuid));

    }

    @ApiOperation("View file")
    @GetMapping("/show/{uuid}")
    public ResponseEntity<Resource> show(@PathVariable String uuid) {
        try {
            ResponseEntity<Resource> resource = fileStorageService.show(uuid);
            log.debug(" <- get: Resource = {}", resource.hashCode());
            return resource;
        } catch (IOException e) {
            log.debug("Can't show file: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
