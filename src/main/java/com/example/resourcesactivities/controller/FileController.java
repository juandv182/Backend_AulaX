package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.ResourceFileDTO;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.ResourceFile;
import com.example.resourcesactivities.repository.MyResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.resourcesactivities.repository.FileRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private MyResourceRepository myResourceRepository;

    @Autowired
    private AmazonS3 amazonS3;
    @GetMapping()
    public ResponseEntity<List<ResourceFile>> getAllFiles() {
        List<ResourceFile> files = fileRepository.findAll();
        if (files.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(files);
    }
    @GetMapping("/file/{name}")
    public ResponseEntity<String> checkIfFileExists(@PathVariable(value = "name") String fileName) {
        Optional<ResourceFile> fileOptional = fileRepository.findByName(fileName);
        if (fileOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("The file '" + fileName + "' exists in the database.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The file '" + fileName + "' does not exist in the database.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFileById(@PathVariable(value = "id") UUID id) {
        ResourceFile file = fileRepository.findById(id).orElse(null);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            Path filePath = Paths.get(file.getFolder());
            Resource resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/resource/{id}")
    public ResponseEntity<List<ResourceFileDTO>> getFileByMyResourceId(@PathVariable(value = "id") Integer myResourceId) {
        List<ResourceFile> files = fileRepository.findByMyResourceId(myResourceId);
        List<ResourceFileDTO> filesDto;
        if (files.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        filesDto = files.stream().map(path -> {
            UUID id = path.getId();
            String name = path.getName();
            Boolean status = path.getStatus();
            LocalDateTime createdAt = path.getCreatedAt();
            LocalDateTime updatedAt = path.getUpdatedAt();
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFileById", path.getId()).build().toString();
            return new ResourceFileDTO(id, name, url, status, createdAt, updatedAt);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(filesDto);
    }

    @PostMapping
    public ResponseEntity<?> createFiles(@RequestParam("files") List<MultipartFile> files,
                                         @RequestParam("resource_id") Integer resourceId) {
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select at least one file.");
        }

        MyResource myResource = myResourceRepository.findMyResourceById(resourceId);
        if (myResource == null) {
            return ResponseEntity.badRequest().body("Resource with id " + resourceId + " not found.");
        }
        String bucketName = "aulax";
        try {
            List<ResourceFile> savedFiles = files.stream()
                    .map(file -> {
                        try {
                            String keyName = resourceId + "/" + file.getOriginalFilename();
                            ObjectMetadata metadata = new ObjectMetadata();
                            metadata.setContentLength(file.getSize());
                            metadata.setContentType(file.getContentType());

                            amazonS3.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), metadata));

                            String fileUrl = amazonS3.getUrl(bucketName, keyName).toString();

                            ResourceFile newFile = new ResourceFile();
                            newFile.setName(file.getOriginalFilename());
                            newFile.setFolder(bucketName + "/" + keyName);
                            newFile.setUrl(fileUrl);
                            newFile.setMyResource(myResource);
                            newFile.setStatus(true);
                            return fileRepository.save(newFile);
                        } catch (IOException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedFiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files to S3.");
        }
    }


    private void createFolderIfNotExists(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}