package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.MyResourceDTO;
import com.example.resourcesactivities.dto.ResourceFileDTO;
import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.dto.TypeFileDTO;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.ResourceFile;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.model.TypeFile;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.repository.TypeFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.resourcesactivities.repository.FileRepository;
import org.springframework.expression.ExpressionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import javax.annotation.Resource;


@RestController
@RequestMapping(value="/files")
@CrossOrigin(originPatterns = "*")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private MyResourceRepository myResourceRepository;
    @Autowired
    private TypeFileRepository tipeFileRepository;
    @Autowired
    private AmazonS3 amazonS3;
    @Transactional
    @GetMapping()
    public ResponseEntity<List<ResourceFileDTO>> getAllFiles() {
        List<ResourceFile> files = fileRepository.findAll();

        if (files.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ResourceFileDTO> filesDTO = files.stream().map(f->{
            TopicDTO topicDTO = TopicDTO.builder()
                    .id(f.getMyResource().getTopic().getId())
                    .name(f.getMyResource().getTopic().getName())
                    .description((f.getMyResource().getTopic().getDescription()))
                    .course((f.getMyResource().getTopic().getCourse()))
                    .learningUnit(f.getMyResource().getTopic().getLearningUnit())
                    .competence(f.getMyResource().getTopic().getCompetence())
                    .status(f.getMyResource().getTopic().getStatus())
                    .createdAt(f.getMyResource().getTopic().getCreatedAt())
                    .updatedAt(f.getMyResource().getTopic().getUpdatedAt())
                    .build();
            return ResourceFileDTO.builder()
                    .id(f.getId())
                    .name(f.getName())
                    .url(f.getUrl())
                    .status((f.getStatus()))
                    .createdAt(f.getCreatedAt())
                    .updatedAt(f.getUpdatedAt())
                    .resource(MyResourceDTO.builder()
                                    .id(f.getMyResource().getId())
                    .name(f.getMyResource().getName())
                    .description(f.getMyResource().getDescription())
                    .topic(topicDTO)
                    .status(f.getMyResource().getStatus())
                    .createdAt(f.getMyResource().getCreatedAt())
                    .updatedAt(f.getMyResource().getUpdatedAt())
                                    .build()
                    )
                    .typeFile(TypeFileDTO.builder()
                            .id(f.getTypeFile().getId())
                            .name(f.getTypeFile().getName())
                            .build())
                    .build();

        }).collect(Collectors.toList());
        return ResponseEntity.ok(filesDTO);
    }
    @Transactional
    @GetMapping("/file/{name}")
    public ResponseEntity<String> checkIfFileExists(@PathVariable(value = "name") String fileName) {
        Optional<ResourceFile> fileOptional = fileRepository.findByName(fileName);
        if (fileOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body("The file '" + fileName + "' exists in the database.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The file '" + fileName + "' does not exist in the database.");
        }
    }
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<ResourceFileDTO> getFileById(@PathVariable(value = "id") Integer id) {
        ResourceFile f=fileRepository.findById(id).
                orElseThrow(() -> new ExpressionException("Archivo no encontrado con ID: " + id));
        Topic t = f.getMyResource().getTopic();
        TopicDTO topicDTO = TopicDTO.builder()
                .id(t.getId())
                .name(t.getName())
                .description((t.getDescription()))
                .course((t.getCourse()))
                .learningUnit(t.getLearningUnit())
                .competence(t.getCompetence())
                .status(t.getStatus())
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
        ResourceFileDTO fileDTO = ResourceFileDTO.builder()
                .id(f.getId())
                .name(f.getName())
                .url(f.getUrl())
                .status((f.getStatus()))
                .createdAt(f.getCreatedAt())
                .updatedAt(f.getUpdatedAt())
                .resource(MyResourceDTO.builder()
                        .id(f.getMyResource().getId())
                        .name(f.getMyResource().getName())
                        .description(f.getMyResource().getDescription())
                        .topic(topicDTO)
                        .status(f.getMyResource().getStatus())
                        .createdAt(f.getMyResource().getCreatedAt())
                        .updatedAt(f.getMyResource().getUpdatedAt())
                        .build())
                .typeFile(TypeFileDTO.builder()
                        .id(f.getTypeFile().getId())
                        .name(f.getTypeFile().getName())
                        .build()

                )
                .build();

        return fileDTO != null ? ResponseEntity.ok().body(fileDTO) : ResponseEntity.notFound().build();
    }
    @Transactional
    @GetMapping("/{topicId}/topic/{typeFileId}/typeFile")
    public ResponseEntity<List<ResourceFileDTO>> getFileByTopicIdAndTypeFile(@PathVariable Integer topicId,@PathVariable Integer typeFileId) {
        List<ResourceFile> files=fileRepository.findByTypeFileId(typeFileId);
        List<ResourceFileDTO> fDTO= files.stream().map(f->{

                    Topic t = f.getMyResource().getTopic();
                    TopicDTO topicDTO = TopicDTO.builder()
                            .id(t.getId())
                            .name(t.getName())
                            .description((t.getDescription()))
                            .course((t.getCourse()))
                            .learningUnit(t.getLearningUnit())
                            .competence(t.getCompetence())
                            .status(t.getStatus())
                            .createdAt(t.getCreatedAt())
                            .updatedAt(t.getUpdatedAt())
                            .build();
                    return ResourceFileDTO.builder()
                            .id(f.getId())
                            .name(f.getName())
                            .url(f.getUrl())
                            .status((f.getStatus()))
                            .createdAt(f.getCreatedAt())
                            .updatedAt(f.getUpdatedAt())
                            .resource(MyResourceDTO.builder()
                                    .id(f.getMyResource().getId())
                                    .name(f.getMyResource().getName())
                                    .description(f.getMyResource().getDescription())
                                    .topic(topicDTO)
                                    .status(f.getMyResource().getStatus())
                                    .createdAt(f.getMyResource().getCreatedAt())
                                    .updatedAt(f.getMyResource().getUpdatedAt())
                                    .build())
                            .typeFile(TypeFileDTO.builder()
                                    .id(f.getTypeFile().getId())
                                    .name(f.getTypeFile().getName())
                                    .build()

                            )
                            .build();

        }).filter(f->f.getResource().getTopic().getId()==topicId).collect(Collectors.toList());


        return ResponseEntity.ok(fDTO);
    }

    @Transactional
    @GetMapping("/resource/{id}")
    public ResponseEntity<List<ResourceFileDTO>> getFileByMyResourceId(@PathVariable(value = "id") Integer myResourceId) {
        List<ResourceFile> files = fileRepository.findByMyResourceId(myResourceId);
        List<ResourceFileDTO> filesDto;
        if (files.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        filesDto = files.stream().map(path -> {
            Integer id = path.getId();
            String name = path.getName();
            Boolean status = path.getStatus();
            LocalDateTime createdAt = path.getCreatedAt();
            LocalDateTime updatedAt = path.getUpdatedAt();
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFileById", path.getId()).build().toString();
            return new ResourceFileDTO(id, name, url, status, createdAt, updatedAt);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(filesDto);
    }
    @Transactional
    @GetMapping("/typefile/{id}")
    public ResponseEntity<List<ResourceFileDTO>> getFileByTypeFileId(@PathVariable(value = "id") Integer typeFileId) {
        List<ResourceFile> files = fileRepository.findByTypeFileId(typeFileId);
        List<ResourceFileDTO> filesDto;
        if (files.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        filesDto = files.stream().map(path -> {
            Integer id = path.getId();
            String name = path.getName();
            Boolean status = path.getStatus();
            LocalDateTime createdAt = path.getCreatedAt();
            LocalDateTime updatedAt = path.getUpdatedAt();
            String url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "getFileById", path.getId()).build().toString();
            return new ResourceFileDTO(id, name, url, status, createdAt, updatedAt);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(filesDto);
    }
    @Transactional
    @PostMapping("/s3")
    public ResponseEntity<?> createFilesS3(@RequestParam("files") List<MultipartFile> files,
                                         @RequestParam("resource_id") Integer resourceId,@RequestParam("typefile_id") Integer typefileId) {
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select at least one file.");
        }

        MyResource myResource = myResourceRepository.findMyResourceById(resourceId);
        if (myResource == null) {
            return ResponseEntity.badRequest().body("Resource with id " + resourceId + " not found.");
        }
        Optional<TypeFile> typeFile = tipeFileRepository.findById(typefileId);
        if (!(typeFile.isPresent())) {
            return ResponseEntity.badRequest().body("TypeFile with id " + typefileId + " not found.");
        }

        String bucketName = "aulax";
        try {
            List<ResourceFileDTO> savedFilesDTO = files.stream()
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
                            newFile.setUrl(fileUrl);
                            newFile.setMyResource(myResource);
                            newFile.setTypeFile(typeFile.orElseThrow());
                            newFile.setStatus(true);
                            fileRepository.save(newFile);
                            TopicDTO topicDTO = TopicDTO.builder()
                                    .id(myResource.getTopic().getId())
                                    .name(myResource.getTopic().getName())
                                    .description((myResource.getTopic().getDescription()))
                                    .course((myResource.getTopic().getCourse()))
                                    .learningUnit(myResource.getTopic().getLearningUnit())
                                    .competence(myResource.getTopic().getCompetence())
                                    .status(myResource.getTopic().getStatus())
                                    .createdAt(myResource.getTopic().getCreatedAt())
                                    .updatedAt(myResource.getTopic().getUpdatedAt())
                                    .build();
                            ResourceFileDTO fileDTO = ResourceFileDTO.builder()
                                    .id(newFile.getId())
                                    .name(newFile.getName())
                                    .url(newFile.getUrl())
                                    .status((newFile.getStatus()))
                                    .createdAt(newFile.getCreatedAt())
                                    .updatedAt(newFile.getUpdatedAt())
                                    .resource(MyResourceDTO.builder()
                                            .id(myResource.getId())
                                            .name(myResource.getName())
                                            .description(myResource.getDescription())
                                            .topic(topicDTO)
                                            .status(myResource.getStatus())
                                            .createdAt(myResource.getCreatedAt())
                                            .updatedAt(myResource.getUpdatedAt())
                                            .build())
                                    .typeFile(TypeFileDTO.builder()
                                            .id(typeFile.orElseThrow().getId())
                                            .name(typeFile.orElseThrow().getName())
                                            .build()

                                    )
                                    .build();
                            return fileDTO;
                        } catch (IOException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.CREATED).body(savedFilesDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files to S3.");
        }
    }
    @Transactional
    @PostMapping
    public ResponseEntity<ResourceFileDTO> createFile(@RequestBody ResourceFileDTO fileDTO) {
        try {

            TypeFileDTO typeFileDTO = fileDTO.getTypeFile();
            TypeFile typeFile = new TypeFile();
            typeFile.setId(typeFileDTO.getId());
            typeFile.setName(typeFileDTO.getName());
            MyResourceDTO resourceDTO = fileDTO.getResource();
            MyResource resource = new MyResource();
            resource.setDescription(resourceDTO.getDescription());
            resource.setCreatedAt(resourceDTO.getCreatedAt());
            resource.setId(resourceDTO.getId());
            resource.setName(resourceDTO.getName());
            resource.setUpdatedAt(resourceDTO.getUpdatedAt());
            ResourceFile file = new ResourceFile();
            file.setTypeFile(typeFile);
            file.setMyResource(resource);
            file.setCreatedAt(fileDTO.getCreatedAt());
            file.setStatus(fileDTO.getStatus());
            file.setUrl(fileDTO.getUrl());
            file.setName(fileDTO.getName());
            file.setCreatedAt(fileDTO.getCreatedAt());
            file.setUpdatedAt(fileDTO.getUpdatedAt());
            ResourceFile savedFile = fileRepository.save(file);
            fileDTO.setId(savedFile.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(fileDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}