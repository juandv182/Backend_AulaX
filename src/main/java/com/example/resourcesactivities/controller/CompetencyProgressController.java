package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.CompetencyProgressDTO;
import com.example.resourcesactivities.service.CompetencyProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progressByCompetency")
@CrossOrigin(origins = "*")
public class CompetencyProgressController {

    @Autowired
    private CompetencyProgressService competencyProgressService;

    @GetMapping("/course/{courseId}/user/{userId}")
    public ResponseEntity<List<CompetencyProgressDTO>> getProgressByCompetency(@PathVariable Integer courseId, @PathVariable Long userId) {
        List<CompetencyProgressDTO> progress = competencyProgressService.getProgressByCompetency(courseId, userId);
        return ResponseEntity.ok(progress);
    }
}
