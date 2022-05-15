package com.yagodda.models;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "files")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("File path model")
public class FilePath {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "store_path")
    private String storePath;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @PrePersist
    private void onCreate() {
        created = LocalDateTime.now();
    }
}
