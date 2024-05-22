package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.ActivityDTO;
import com.example.resourcesactivities.dto.MyResourceDTO;
import com.example.resourcesactivities.dto.ResourceFileDTO;
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
    private TopicRepository topicRepository; // Agregar repositorio de Topic

    public List<MyResourceDTO> getAllResources() {
        return myResourceRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public MyResourceDTO getResourceById(Integer resourceId) {
        Optional<MyResource> resource = myResourceRepository.findById(resourceId);
        return resource.map(this::convertToDTO).orElse(null);
    }

    public List<ResourceFileDTO> getFilesByResourceId(Integer resourceId) {
        return fileRepository.findByMyResourceId(resourceId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public MyResourceDTO createResource(MyResourceDTO myResourceDTO) {
        MyResource myResource = new MyResource();
        myResource.setName(myResourceDTO.getName());
        myResource.setDescription(myResourceDTO.getDescription());
        myResource.setStatus(myResourceDTO.getStatus());

        // Verificar que topicId no sea null y que el Topic exista
        if (myResourceDTO.getTopicId() != null) {
            Optional<Topic> topic = topicRepository.findById(myResourceDTO.getTopicId());
            if (topic.isPresent()) {
                myResource.setTopic(topic.get());
            } else {
                throw new IllegalArgumentException("Invalid topicId");
            }
        }

        Set<ResourceFile> files = myResourceDTO.getFiles().stream().map(fileDTO -> {
            ResourceFile file = new ResourceFile();
            file.setName(fileDTO.getName());
            file.setUrl(fileDTO.getUrl());
            file.setStatus(fileDTO.getStatus());
            file.setMyResource(myResource);
            return file;
        }).collect(Collectors.toSet());  // Cambiar a Collectors.toSet()
        myResource.setFiles(files);

        MyResource savedResource = myResourceRepository.save(myResource);
        return convertToDTO(savedResource);
    }

    @Transactional
    public MyResourceDTO updateResource(Integer resourceId, MyResourceDTO myResourceDTO) {
        Optional<MyResource> optionalResource = myResourceRepository.findById(resourceId);
        if (optionalResource.isPresent()) {
            MyResource existingMyResource = optionalResource.get();
            existingMyResource.setName(myResourceDTO.getName());
            existingMyResource.setDescription(myResourceDTO.getDescription());
            existingMyResource.setStatus(myResourceDTO.getStatus());

            // Verificar que topicId no sea null y que el Topic exista
            if (myResourceDTO.getTopicId() != null) {
                Optional<Topic> topic = topicRepository.findById(myResourceDTO.getTopicId());
                if (topic.isPresent()) {
                    existingMyResource.setTopic(topic.get());
                } else {
                    throw new IllegalArgumentException("Invalid topicId");
                }
            }

            MyResource updatedMyResource = myResourceRepository.save(existingMyResource);
            return convertToDTO(updatedMyResource);
        } else {
            return null;
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

    public List<MyResourceDTO> searchResources(Integer topicId, List<Integer> competencies, List<Integer> courses) {
        // Implementar lógica de búsqueda
        return null;
    }

    private MyResourceDTO convertToDTO(MyResource myResource) {
        Set<ResourceFileDTO> fileDTOs = myResource.getFiles().stream().map(file ->
                new ResourceFileDTO(file.getId(), file.getName(), file.getUrl(), file.getStatus(), file.getCreatedAt(), file.getUpdatedAt())
        ).collect(Collectors.toSet());

        // Convertir actividades de MyResource a ActivityDTO
        Set<ActivityDTO> activityDTOs = myResource.getActivities().stream().map(activity ->
                new ActivityDTO(activity.getId(), activity.getName(), activity.getDescription(), activity.getStatus(), activity.getCreatedAt(), activity.getUpdatedAt(), activity.getMyResource().getId(), activity.getTypeActivity().getId())
        ).collect(Collectors.toSet());

        return new MyResourceDTO(myResource.getId(), myResource.getName(), myResource.getDescription(), myResource.getTopic().getId(), myResource.getStatus(), myResource.getCreatedAt(), myResource.getUpdatedAt(), fileDTOs, activityDTOs);
    }

    private ResourceFileDTO convertToDTO(ResourceFile resourceFile) {
        return new ResourceFileDTO(resourceFile.getId(), resourceFile.getName(), resourceFile.getUrl(), resourceFile.getStatus(), resourceFile.getCreatedAt(), resourceFile.getUpdatedAt());
    }
}
