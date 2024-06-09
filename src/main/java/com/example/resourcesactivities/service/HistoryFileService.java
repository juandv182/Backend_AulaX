package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.HistoryFileDTO;
import com.example.resourcesactivities.dto.ResourceFileDTO;
import com.example.resourcesactivities.model.HistoryFile;
import com.example.resourcesactivities.model.ResourceFile;
import com.example.resourcesactivities.repository.FileRepository;
import com.example.resourcesactivities.repository.HistoryFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoryFileService {
    @Autowired
    private HistoryFileRepository repository;
    @Autowired
    private FileRepository fileRepository;
    @Transactional
    public List<HistoryFileDTO> getAll(){
         List<HistoryFile> hflist= repository.findAll();
         List<HistoryFileDTO> hflistDTO= hflist.stream().map(h-> {
                     ResourceFileDTO rf = ResourceFileDTO.builder()
                             .id(h.getFile().getId())
                             .name(h.getFile().getName())
                             .url(h.getFile().getUrl())
                             .createdAt(h.getFile().getCreatedAt())
                             .build();
                     return HistoryFileDTO.builder()
                             .id(h.getId())
                             .file(rf)
                             .clickedAt(h.getClickedAt())
                             .build();
                 }).collect(Collectors.toList());


         return hflistDTO;
    }
    @Transactional
    public HistoryFile save(HistoryFile hf){
        HistoryFile hfsaved = new HistoryFile();
        Optional<ResourceFile> file=fileRepository.findById(hf.getFile().getId());
        if(file.isPresent()){
            hfsaved.setFile(file.orElseThrow());
            hfsaved.setId(hf.getId());
        }

        return repository.save(hfsaved);
    }


}
