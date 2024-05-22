package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.ActivityDTO;
import com.example.resourcesactivities.dto.TypeActivityDTO;
import com.example.resourcesactivities.dto.ResourceFileDTO;
import com.example.resourcesactivities.model.Activity;
import com.example.resourcesactivities.model.MyResource;
import com.example.resourcesactivities.model.ResourceFile;
import com.example.resourcesactivities.model.TypeActivity;
import com.example.resourcesactivities.repository.ActivityRepository;
import com.example.resourcesactivities.repository.FileRepository;
import com.example.resourcesactivities.repository.MyResourceRepository;
import com.example.resourcesactivities.repository.TypeActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/types_activities")
@CrossOrigin(originPatterns = "*")
public class TypeActivityController {

    @Autowired
    private TypeActivityRepository typeActivityRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private MyResourceRepository myResourceRepository;

    @Transactional
    @GetMapping
    public List<TypeActivityDTO> getAllTypeActivities() {
        return typeActivityRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<TypeActivityDTO> getTypeActivityById(@PathVariable(value = "id") Integer typeActivityId) {
        Optional<TypeActivity> typeActivity = typeActivityRepository.findById(typeActivityId);
        return typeActivity.map(value -> ResponseEntity.ok().body(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    @GetMapping("/{id}/files")
    public ResponseEntity<List<ResourceFileDTO>> getFilesByTypeActivityId(@PathVariable(value = "id") Integer typeActivityId) {
        List<MyResource> resources = myResourceRepository.findByTypeActivityId(typeActivityId);
        if (resources != null && !resources.isEmpty()) {
            List<ResourceFileDTO> files = resources.stream()
                    .flatMap(resource -> resource.getFiles().stream())
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(files);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PostMapping
    public ResponseEntity<TypeActivityDTO> createTypeActivity(@RequestBody TypeActivityDTO typeActivityDTO) {
        TypeActivity typeActivity = convertToEntity(typeActivityDTO);
        TypeActivity savedTypeActivity = typeActivityRepository.save(typeActivity);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedTypeActivity));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<TypeActivityDTO> updateTypeActivity(@PathVariable(value = "id") Integer typeActivityId,
                                                              @RequestBody TypeActivityDTO typeActivityDTO) {
        Optional<TypeActivity> optionalTypeActivity = typeActivityRepository.findById(typeActivityId);
        if (optionalTypeActivity.isPresent()) {
            TypeActivity existingTypeActivity = optionalTypeActivity.get();
            existingTypeActivity.setName(typeActivityDTO.getName());
            existingTypeActivity.setDescription(typeActivityDTO.getDescription());
            existingTypeActivity.setStatus(typeActivityDTO.getStatus());
            TypeActivity updatedTypeActivity = typeActivityRepository.save(existingTypeActivity);
            return ResponseEntity.ok(convertToDTO(updatedTypeActivity));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeActivity(@PathVariable(value = "id") Integer typeActivityId) {
        Optional<TypeActivity> typeActivity = typeActivityRepository.findById(typeActivityId);
        if (typeActivity.isPresent()) {
            typeActivityRepository.delete(typeActivity.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private TypeActivityDTO convertToDTO(TypeActivity typeActivity) {
        List<ActivityDTO> activityDTOs = typeActivity.getActivities().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new TypeActivityDTO(
                typeActivity.getId(),
                typeActivity.getName(),
                typeActivity.getDescription(),
                typeActivity.getStatus(),
                activityDTOs
        );
    }

    private ActivityDTO convertToDTO(Activity activity) {
        return new ActivityDTO(
                activity.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getStatus(),
                activity.getCreatedAt(),
                activity.getUpdatedAt(),
                activity.getMyResource() != null ? activity.getMyResource().getId() : null,
                activity.getTypeActivity() != null ? activity.getTypeActivity().getId() : null
        );
    }

    private ResourceFileDTO convertToDTO(ResourceFile resourceFile) {
        return new ResourceFileDTO(
                resourceFile.getId(),
                resourceFile.getName(),
                resourceFile.getUrl(),
                resourceFile.getStatus(),
                resourceFile.getCreatedAt(),
                resourceFile.getUpdatedAt()
        );
    }

    private TypeActivity convertToEntity(TypeActivityDTO typeActivityDTO) {
        TypeActivity typeActivity = new TypeActivity();
        typeActivity.setName(typeActivityDTO.getName());
        typeActivity.setDescription(typeActivityDTO.getDescription());
        typeActivity.setStatus(typeActivityDTO.getStatus());
        if (typeActivityDTO.getActivities() != null) {
            List<Activity> activities = typeActivityDTO.getActivities().stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
            typeActivity.setActivities(activities);
        }
        return typeActivity;
    }

    private Activity convertToEntity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setName(activityDTO.getName());
        activity.setDescription(activityDTO.getDescription());
        activity.setStatus(activityDTO.getStatus());
        activity.setCreatedAt(activityDTO.getCreatedAt());
        activity.setUpdatedAt(activityDTO.getUpdatedAt());
        if (activityDTO.getMyResourceId() != null) {
            Optional<MyResource> optionalMyResource = myResourceRepository.findById(activityDTO.getMyResourceId());
            optionalMyResource.ifPresent(activity::setMyResource);
        }
        if (activityDTO.getTypeActivityId() != null) {
            Optional<TypeActivity> optionalTypeActivity = typeActivityRepository.findById(activityDTO.getTypeActivityId());
            optionalTypeActivity.ifPresent(activity::setTypeActivity);
        }
        return activity;
    }
}
