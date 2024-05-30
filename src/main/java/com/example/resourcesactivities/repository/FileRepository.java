package com.example.resourcesactivities.repository;

import com.example.resourcesactivities.model.ResourceFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileRepository extends JpaRepository<ResourceFile, UUID> {
    List<ResourceFile> findByMyResourceId(Integer myResourceId);
    List<ResourceFile> findByTypeFileId(Integer typeFileId);

    Optional<ResourceFile> findByName(String name);
}
