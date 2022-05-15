package com.yagodda.repositories;

import com.yagodda.models.FilePath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilePathRepository extends JpaRepository<FilePath, Long> {
    Optional<FilePath> findByStorePath(String storePath);

    Optional<FilePath> findByUuid(String uuid);
}
