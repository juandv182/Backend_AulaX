package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    private MyResourceRepository myResourceRepository;

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public List<MyResource> getAllResources() {
        return myResourceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyResource> getResourceById(@PathVariable(value = "id") Integer resourceId) {
        Optional<MyResource> resource = myResourceRepository.findById(resourceId);
        return resource.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MyResource> createResource(@RequestBody MyResource myResource) {
        MyResource savedMyResource = myResourceRepository.save(myResource);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMyResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyResource> updateResource(@PathVariable(value = "id") Integer resourceId,
                                                     @RequestBody MyResource myResourceDetails) {
        Optional<MyResource> optionalResource = myResourceRepository.findById(resourceId);
        if (optionalResource.isPresent()) {
            MyResource existingMyResource = optionalResource.get();
            existingMyResource.setName(myResourceDetails.getName());
            existingMyResource.setDescription(myResourceDetails.getDescription());
            existingMyResource.setTopic(myResourceDetails.getTopic());
            existingMyResource.setStatus(myResourceDetails.getStatus());
            MyResource updatedMyResource = myResourceRepository.save(existingMyResource);
            return ResponseEntity.ok(updatedMyResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable(value = "id") Integer resourceId) {
        Optional<MyResource> resource = myResourceRepository.findById(resourceId);
        if (resource.isPresent()) {
            myResourceRepository.delete(resource.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<MyResource>> searchResources(@RequestBody Map<String, Object> requestBody) {
        Integer topicId = (Integer) requestBody.get("topic_id");
        List<Integer> competencies = (List<Integer>) requestBody.get("competencies");
        List<Integer> courses = (List<Integer>) requestBody.get("courses");

        List<MyResource> myResources = resourceService.searchResources(topicId, competencies, courses);
        if (myResources.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(myResources);
    }
}