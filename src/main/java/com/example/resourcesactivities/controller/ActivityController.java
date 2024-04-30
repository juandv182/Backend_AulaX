package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.Activity;
import com.example.resourcesactivities.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activities")
@CrossOrigin(originPatterns = "*")
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable(value = "id") Integer activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        return activity.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        Activity savedActivity = activityRepository.save(activity);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable(value = "id") Integer activityId,
                                                   @RequestBody Activity activityDetails) {
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if (optionalActivity.isPresent()) {
            Activity existingActivity = optionalActivity.get();
            existingActivity.setName(activityDetails.getName());
            existingActivity.setDescription(activityDetails.getDescription());
            existingActivity.setMyResource(activityDetails.getMyResource());
            existingActivity.setTypeActivity(activityDetails.getTypeActivity());
            existingActivity.setStatus(activityDetails.getStatus());
            Activity updatedActivity = activityRepository.save(existingActivity);
            return ResponseEntity.ok(updatedActivity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable(value = "id") Integer activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        if (activity.isPresent()) {
            activityRepository.delete(activity.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
