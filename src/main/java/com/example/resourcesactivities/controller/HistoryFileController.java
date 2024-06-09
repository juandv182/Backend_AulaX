package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.HistoryFileDTO;
import com.example.resourcesactivities.dto.QuizzDTO;
import com.example.resourcesactivities.model.HistoryFile;
import com.example.resourcesactivities.repository.HistoryFileRepository;
import com.example.resourcesactivities.service.HistoryFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/history-files")
@CrossOrigin(originPatterns = "*")
public class HistoryFileController {
    @Autowired
    private HistoryFileRepository historyFileRepository;
    @Autowired
    private HistoryFileService historyFileService;
    @GetMapping
    public ResponseEntity<List<HistoryFileDTO>> getAllHistoryFiles() {
        List<HistoryFileDTO> list= historyFileService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<HistoryFile> create(@RequestBody  HistoryFile historyFile) {
        historyFile.setClickedAt(LocalDateTime.now());
        HistoryFile savedHistoryFile = historyFileRepository.save(historyFile);
        return new ResponseEntity<>(savedHistoryFile, HttpStatus.CREATED);
    }
}
