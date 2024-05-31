package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.AlternativeDTO;
import com.example.resourcesactivities.dto.QuestionDTO;
import com.example.resourcesactivities.model.Alternative;
import com.example.resourcesactivities.model.Question;
import com.example.resourcesactivities.repository.AlternativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlternativeService {
    @Autowired
    private AlternativeRepository alternativeRepository;

    @Transactional
    public List<AlternativeDTO> getAll() {
        List<Alternative> list = alternativeRepository.findAll();
        List<AlternativeDTO> DTOlist = list.stream().map(q ->
                AlternativeDTO.builder()
                        .id(q.getId())
                        .value(q.getValue())
                        .is_answer(q.getIs_answer())
                        .is_marked(q.getIs_marked())
                        .createdAt(q.getCreatedAt())
                        .updatedAt(q.getUpdatedAt())
                        .question(QuestionDTO.builder()
                                .id(q.getQuestion().getId())
                                .build())
                        .build()).collect(Collectors.toList());
        return DTOlist;
    }

    @Transactional
    public AlternativeDTO getById(Integer id) {
        Alternative alternative = alternativeRepository.findById(id).
                orElseThrow(() -> new ExpressionException("Alternativa no encontrado con ID: " + id));

        return AlternativeDTO.builder()
                .id(alternative.getId())
                .value(alternative.getValue())
                .is_answer(alternative.getIs_answer())
                .is_marked(alternative.getIs_marked())
                .createdAt(alternative.getCreatedAt())
                .updatedAt(alternative.getUpdatedAt())
                .question(QuestionDTO.builder()
                        .id(alternative.getQuestion().getId())
                        .build())
                .build();

    }

    @Transactional
    public List<AlternativeDTO> findByQuestionId(Integer id) {
        return alternativeRepository.findByQuestionId(id).stream().map(
                alternative -> {
                    return AlternativeDTO.builder()
                            .id(alternative.getId())
                            .value(alternative.getValue())
                            .is_answer(alternative.getIs_answer())
                            .is_marked(alternative.getIs_marked())
                            .createdAt(alternative.getCreatedAt())
                            .updatedAt(alternative.getUpdatedAt())
                            .question(QuestionDTO.builder()
                                    .id(alternative.getQuestion().getId())
                                    .build())
                            .build();
                }).collect(Collectors.toList());

    }

    @Transactional
    public AlternativeDTO save(AlternativeDTO alternativeDTO) {
        Alternative al = new Alternative();
        al.setValue(alternativeDTO.getValue());
        al.setIs_answer(alternativeDTO.getIs_answer());
        al.setIs_marked(alternativeDTO.getIs_marked());
        al.setCreatedAt(alternativeDTO.getCreatedAt());
        al.setUpdatedAt(alternativeDTO.getUpdatedAt());
        Question q=new Question();
        q.setId(alternativeDTO.getQuestion().getId());
        al.setQuestion(q);
        al.setId(alternativeDTO.getId());
        alternativeRepository.save(al);
        alternativeDTO.setId(al.getId());
        return alternativeDTO;
    }
    @Transactional
    public void update(AlternativeDTO alternativeDTO){
        alternativeRepository.findById(alternativeDTO.getId()).
                orElseThrow(() -> new RuntimeException("No se encontro la alternativa a actualizar"));
        Alternative al = new Alternative();
        al.setValue(alternativeDTO.getValue());
        al.setIs_answer(alternativeDTO.getIs_answer());
        al.setIs_marked(alternativeDTO.getIs_marked());
        al.setCreatedAt(alternativeDTO.getCreatedAt());
        al.setUpdatedAt(alternativeDTO.getUpdatedAt());
        Question q=new Question();
        q.setId(alternativeDTO.getQuestion().getId());
        al.setQuestion(q);
        alternativeRepository.save(al);
    }
    @Transactional
    public void delete(Integer id) {
        alternativeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alternativa no encontrado con ID: " + id));
        alternativeRepository.deleteById(id);
    }
}