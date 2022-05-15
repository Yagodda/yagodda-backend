package com.yagodda.services.impl;

import com.yagodda.configurations.FileStorageConfiguration;
import com.yagodda.models.FilePath;
import com.yagodda.repositories.FilePathRepository;
import com.yagodda.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {
    private final FilePathRepository filePathRepository;
    private final FileStorageConfiguration storageConfiguration;

    @Override
    public String upload(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();

        FilePath filePathEntity = new FilePath();
        filePathEntity.setOriginalFileName(file.getOriginalFilename());
        filePathEntity.setMimeType(Objects.requireNonNull(file.getContentType()).split(" ")[0]);
        filePathEntity.setUuid(uuid);

        String ext = filePathEntity.getOriginalFileName()
                .substring(filePathEntity.getOriginalFileName().lastIndexOf("."));

        this.store(storageConfiguration.getPath(), uuid + ext, file.getBytes());

        filePathEntity.setStorePath(storageConfiguration.getPath() + uuid + ext);

        log.info(" _. File \"{}\" stored to disk with UUID \"{}\"", filePathEntity.getOriginalFileName(), uuid);

        return uuid;
    }

    @Override
    public ResponseEntity<Resource> show(String uuid) throws IOException {
        Optional<FilePath> optionalFilePath = filePathRepository.findByUuid(uuid);
        if (optionalFilePath.isPresent()) {
            FilePath filePath = optionalFilePath.get();

            byte[] file = new byte[0];

            try {
                file = Files.readAllBytes(Paths.get(filePath.getStorePath()));
            } catch (IOException e) {
                log.debug("Can't get file \"{}\" for showing: {}", filePath.getOriginalFileName(), e.getMessage());
            }

            String mediaType = filePath.getMimeType().split("/")[0];
            String mediaSubType = filePath.getMimeType().split("/")[1];

            return ResponseEntity.ok()
                    .contentType(new MediaType(mediaType, mediaSubType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=" + URLEncoder.encode(filePath.getOriginalFileName(), StandardCharsets.UTF_8))
                    .body(new ByteArrayResource(file));
        } else {
            File tmpFolder = new File(storageConfiguration.getPath());
            String tmpFileName = Objects.requireNonNull(tmpFolder.list((dir, name) -> name.startsWith(uuid)))[0];

            File tmpFile = new File(storageConfiguration.getPath() + tmpFileName);
            String tmpFileMimeType = Files.probeContentType(tmpFile.toPath());

            String mediaType = tmpFileMimeType.split("/")[0];
            String mediaSubType = tmpFileMimeType.split("/")[1];

            return ResponseEntity.ok()
                    .contentType(new MediaType(mediaType, mediaSubType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=" + URLEncoder.encode(uuid, StandardCharsets.UTF_8))
                    .body(new ByteArrayResource(Files.readAllBytes(tmpFile.toPath())));
        }
    }

    private void store(String storagePath, String fileName, byte[] data) {
        String filePath = storagePath + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data);
        } catch (IOException e) {
            log.error("Can't store file to {} : {}", filePath, e.getMessage());
        }
    }
}
