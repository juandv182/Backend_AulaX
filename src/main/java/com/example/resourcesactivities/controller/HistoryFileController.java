package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.model.HistoryFile;
import com.example.resourcesactivities.repository.HistoryFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/history-files")
@CrossOrigin(originPatterns = "*")
public class HistoryFileController {
    @Autowired
    private HistoryFileRepository historyFileRepository;
    @PostMapping
    public ResponseEntity<HistoryFile> create(@RequestBody  HistoryFile historyFile) {
        historyFile.setClickedAt(LocalDateTime.now());
        HistoryFile savedHistoryFile = historyFileRepository.save(historyFile);
        return new ResponseEntity<>(savedHistoryFile, HttpStatus.CREATED);
    }
}
