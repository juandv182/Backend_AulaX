package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.ActivityDTO;
import com.example.resourcesactivities.dto.MyResourceDTO;
import com.example.resourcesactivities.dto.ResourceFileDTO;
import com.example.resourcesactivities.dto.TopicDTO;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.ResourceFile;
import com.example.resourcesactivities.model.Topic;
import com.example.resourcesactivities.repository.FileRepository;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.repository.TopicRepository;
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
    @Transactional
    public List<MyResourceDTO> getAllResources() {
       List<MyResource> resourceList=myResourceRepository.findAll();
       List<MyResourceDTO> resourceDTOList=resourceList.stream().map(r -> {
           Topic topic=r.getTopic();
           TopicDTO topicDTO = TopicDTO.builder()
                   .id(topic.getId())
                   .name(topic.getName())
                   .description((topic.getDescription()))
                   .course((topic.getCourse()))
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
            TopicDTO topicDTO = TopicDTO.builder()
                    .id(topic.getId())
                    .name(topic.getName())
                    .description((topic.getDescription()))
                    .course((topic.getCourse()))
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


    private ResourceFileDTO convertToDTO(ResourceFile resourceFile) {
        return new ResourceFileDTO(resourceFile.getId(),
                resourceFile.getName(),
                resourceFile.getUrl(),
                resourceFile.getStatus(),
                resourceFile.getCreatedAt(),
                resourceFile.getUpdatedAt());
    }
}
