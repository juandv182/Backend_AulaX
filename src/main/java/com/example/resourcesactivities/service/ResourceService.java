package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.*;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.ResourceFile;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.FileRepository;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    @Autowired
    private MyResourceRepository myResourceRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TopicRepository topicRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);
    @Transactional
    public List<MyResourceDTO> getAllResources() {
       List<MyResource> resourceList=myResourceRepository.findAll();
       List<MyResourceDTO> resourceDTOList=resourceList.stream().map(r -> {
           Topic topic=r.getTopic();
           CourseDTO cdto = CourseDTO.builder()
                   .id(topic.getCourse().getId())
                   .name(topic.getCourse().getName())
                   .description(topic.getCourse().getDescription())
                   .build();
           TopicDTO topicDTO = TopicDTO.builder()
                   .id(topic.getId())
                   .name(topic.getName())
                   .description((topic.getDescription()))
                   .course(cdto)
                   .learningUnit(topic.getLearningUnit())
                   .competence(topic.getCompetence())
                   .status(topic.getStatus())
                   .createdAt(topic.getCreatedAt())
                   .updatedAt(topic.getUpdatedAt())
                   .build();
           return MyResourceDTO.builder()
                   .id(r.getId())
                   .name(r.getName())
                   .description(r.getDescription())
                   .topic(topicDTO)
                   .mode(r.getMode())
                   .status(r.getStatus())
                   .createdAt(r.getCreatedAt())
                   .updatedAt(r.getUpdatedAt())
                   .files(r.getFiles().stream().map(f->{
                       return ResourceFileDTO.builder()
                               .id(f.getId())
                               .name(f.getName())
                               .url(f.getUrl())
                               .status((f.getStatus()))
                               .createdAt(f.getCreatedAt())
                               .updatedAt(f.getUpdatedAt())
                               .build();

                   }).collect(Collectors.toSet()))
                   .build();
       }).collect(Collectors.toList());
       return resourceDTOList;
    }
    @Transactional
    public MyResourceDTO getResourceById(Integer resourceId) {
        Optional<MyResource> resource = myResourceRepository.findById(resourceId);
        if (resource.isPresent()){
            Topic topic=resource.orElseThrow().getTopic();
            CourseDTO cdto = CourseDTO.builder()
                    .id(topic.getId())
                    .name(topic.getName())
                    .description(topic.getDescription())
                    .build();
            TopicDTO topicDTO = TopicDTO.builder()
                    .id(topic.getId())
                    .name(topic.getName())
                    .description((topic.getDescription()))
                    .course(cdto)
                    .learningUnit(topic.getLearningUnit())
                    .competence(topic.getCompetence())
                    .status(topic.getStatus())
                    .createdAt(topic.getCreatedAt())
                    .updatedAt(topic.getUpdatedAt())
                    .build();
            return MyResourceDTO.builder()
                    .id(resource.orElseThrow().getId())
                    .name(resource.orElseThrow().getName())
                    .description(resource.orElseThrow().getDescription())
                    .topic(topicDTO)
                    .mode(resource.orElseThrow().getMode())
                    .status(resource.orElseThrow().getStatus())
                    .createdAt(resource.orElseThrow().getCreatedAt())
                    .updatedAt(resource.orElseThrow().getUpdatedAt())
                    .files(resource.orElseThrow().getFiles().stream().map(f->{
                        return ResourceFileDTO.builder()
                                .id(f.getId())
                                .name(f.getName())
                                .url(f.getUrl())
                                .status((f.getStatus()))
                                .createdAt(f.getCreatedAt())
                                .updatedAt(f.getUpdatedAt())
                                .build();

                    }).collect(Collectors.toSet()))
                    .build();
        }else{
            throw new IllegalArgumentException("Invalid resourceId");
        }


    }
    @Transactional
    public List<ResourceFileDTO> getFilesByResourceId(Integer resourceId) {
        return fileRepository.findByMyResourceId(resourceId).stream().map(
                this::convertToDTO).collect(Collectors.toList());
    }
    @Transactional
    public List<MyResourceDTO> getResourcesByTypeFileId(Integer typeFileId) {
        List<MyResource> resources = myResourceRepository.findAll();
        logger.info("Total resources found: {}", resources.size());

        List<MyResourceDTO> filteredResources = resources.stream()
                .map(resource -> {
                    Set<ResourceFile> filteredFiles = resource.getFiles().stream()
                            .filter(file -> file.getTypeFile().getId().equals(typeFileId))
                            .collect(Collectors.toSet());

                    if (!filteredFiles.isEmpty()) {
                        MyResourceDTO dto = convertToDTO(resource);
                        dto.setFiles(filteredFiles.stream().map(this::convertToDTO).collect(Collectors.toSet()));
                        return dto;
                    }
                    return null;
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());

        logger.info("Total filtered resources: {}", filteredResources.size());
        return filteredResources;
    }
    @Transactional
    public MyResourceDTO createResource(MyResourceDTO myResourceDTO) {
        MyResource myResource = new MyResource();
        myResource.setName(myResourceDTO.getName());
        myResource.setDescription(myResourceDTO.getDescription());
        myResource.setStatus(myResourceDTO.getStatus());
        myResource.setCreatedAt(myResourceDTO.getCreatedAt());
        myResource.setUpdatedAt(myResourceDTO.getUpdatedAt());
        // Verificar que topicId no sea null y que el Topic exista
        Optional<Topic> topic = null;
        TopicDTO topicdto = null;
        if (myResourceDTO.getTopic().getId() != null) {
            topicdto = myResourceDTO.getTopic();
            topic = topicRepository.findById(myResourceDTO.getTopic().getId());
            if (topic.isPresent()) {
                myResource.setTopic(topic.get());
            } else {
                throw new IllegalArgumentException("Invalid topicId");
            }
        }

        MyResource savedResource = myResourceRepository.save(myResource);

        return MyResourceDTO.builder()
                .id(savedResource.getId())
                .name(savedResource.getName())
                .description(savedResource.getDescription())
                .topic(topicdto)
                .status(savedResource.getStatus())
                .createdAt(savedResource.getCreatedAt())
                .updatedAt(savedResource.getUpdatedAt())
                .files(savedResource.getFiles().stream().map(f->{
                    return ResourceFileDTO.builder()
                            .id(f.getId())
                            .name(f.getName())
                            .url(f.getUrl())
                            .status((f.getStatus()))
                            .createdAt(f.getCreatedAt())
                            .updatedAt(f.getUpdatedAt())
                            .build();

                }).collect(Collectors.toSet()))
                .build();
    }

    @Transactional
    public MyResourceDTO updateResource(Integer resourceId, MyResourceDTO myResourceDTO) {
        Optional<MyResource> optionalResource = myResourceRepository.findById(resourceId);
        if (optionalResource.isPresent()) {
            MyResource existingMyResource = optionalResource.get();
            existingMyResource.setName(myResourceDTO.getName());
            existingMyResource.setDescription(myResourceDTO.getDescription());
            existingMyResource.setStatus(myResourceDTO.getStatus());
            existingMyResource.setCreatedAt(myResourceDTO.getCreatedAt());
            existingMyResource.setUpdatedAt(myResourceDTO.getUpdatedAt());
            existingMyResource.setId(resourceId);
            // Verificar que topicId no sea null y que el Topic exista
            TopicDTO topicdto = null;
            if (myResourceDTO.getTopic().getId() != null) {
                topicdto=myResourceDTO.getTopic();
                Optional<Topic> topic = topicRepository.findById(myResourceDTO.getTopic().getId());
                if (topic.isPresent()) {
                    existingMyResource.setTopic(topic.get());
                } else {
                    throw new IllegalArgumentException("Invalid topicId");
                }
            }

            MyResource updatedMyResource = myResourceRepository.save(existingMyResource);
            return MyResourceDTO.builder()
                    .id(updatedMyResource.getId())
                    .name(updatedMyResource.getName())
                    .description(updatedMyResource.getDescription())
                    .topic(topicdto)
                    .mode(updatedMyResource.getMode())
                    .status(updatedMyResource.getStatus())
                    .createdAt(updatedMyResource.getCreatedAt())
                    .updatedAt(updatedMyResource.getUpdatedAt())
                    .files(updatedMyResource.getFiles().stream().map(f->{
                        return ResourceFileDTO.builder()
                                .id(f.getId())
                                .name(f.getName())
                                .url(f.getUrl())
                                .status((f.getStatus()))
                                .createdAt(f.getCreatedAt())
                                .updatedAt(f.getUpdatedAt())
                                .build();

                    }).collect(Collectors.toSet()))
                    .build();
        } else {
            throw new IllegalArgumentException("Invalid resourceID");
        }
    }

    @Transactional
    public boolean deleteResource(Integer resourceId) {
        Optional<MyResource> resource = myResourceRepository.findById(resourceId);
        if (resource.isPresent()) {
            myResourceRepository.delete(resource.get());
            return true;
        } else {
            return false;
        }
    }


    private ResourceFileDTO convertToDTO(ResourceFile file) {
        return ResourceFileDTO.builder()
                .id(file.getId())
                .name(file.getName())
                .url(file.getUrl())
                .status(file.getStatus())
                .createdAt(file.getCreatedAt())
                .updatedAt(file.getUpdatedAt())
                .build();
    }
    private MyResourceDTO convertToDTO(MyResource resource) {
        return MyResourceDTO.builder()
                .id(resource.getId())
                .name(resource.getName())
                .mode(resource.getMode())
                .description(resource.getDescription())
                .status(resource.getStatus())
                .createdAt(resource.getCreatedAt())
                .updatedAt(resource.getUpdatedAt())
                .files(resource.getFiles().stream().map(this::convertToDTO).collect(Collectors.toSet()))
                .build();
    }
}
