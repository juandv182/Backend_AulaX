package com.example.resourcesactivities.controller;

import com.example.resourcesactivities.dto.TypeFileDTO;
import com.example.resourcesactivities.model.TypeFile;
import com.example.resourcesactivities.repository.TypeFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/typefiles")
@CrossOrigin(originPatterns = "*")
public class TypeFileController {

    @Autowired
    private TypeFileRepository typeFileRepository;

    @GetMapping
    public ResponseEntity<List<TypeFileDTO>> getAllTypeFiles() {
        List<TypeFile> typeFiles = typeFileRepository.findAll();
        List<TypeFileDTO> typeFilesDTO = typeFiles.stream().map(t->TypeFileDTO.builder()
                .id(t.getId())
                .name(t.getName())
                .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(typeFilesDTO);
    }
}