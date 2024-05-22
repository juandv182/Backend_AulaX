package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.MyResourceDTO;
import com.example.resourcesactivities.dto.ResourceFileDTO;
import com.example.resourcesactivities.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resources")
@CrossOrigin(originPatterns = "*")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping
    public List<MyResourceDTO> getAllResources() {
        return resourceService.getAllResources();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyResourceDTO> getResourceById(@PathVariable(value = "id") Integer resourceId) {
        MyResourceDTO resource = resourceService.getResourceById(resourceId);
        return resource != null ? ResponseEntity.ok().body(resource) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<List<ResourceFileDTO>> getFilesByResourceId(@PathVariable(value = "id") Integer resourceId) {
        List<ResourceFileDTO> files = resourceService.getFilesByResourceId(resourceId);
        return files != null ? ResponseEntity.ok(files) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MyResourceDTO> createResource(@RequestBody MyResourceDTO myResourceDTO) {
        MyResourceDTO savedMyResource = resourceService.createResource(myResourceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMyResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyResourceDTO> updateResource(@PathVariable(value = "id") Integer resourceId,
                                                        @RequestBody MyResourceDTO myResourceDetails) {
        MyResourceDTO updatedMyResource = resourceService.updateResource(resourceId, myResourceDetails);
        return updatedMyResource != null ? ResponseEntity.ok(updatedMyResource) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable(value = "id") Integer resourceId) {
        boolean isDeleted = resourceService.deleteResource(resourceId);
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<MyResourceDTO>> searchResources(@RequestBody Map<String, Object> requestBody) {
        Integer topicId = (Integer) requestBody.get("topic_id");
        List<Integer> competencies = (List<Integer>) requestBody.get("competencies");
        List<Integer> courses = (List<Integer>) requestBody.get("courses");

        List<MyResourceDTO> myResources = resourceService.searchResources(topicId, competencies, courses);
        return myResources.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(myResources);
    }
}
