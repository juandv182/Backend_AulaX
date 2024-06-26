package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.*;
import com.example.resourcesactivities.model.*;
import com.example.resourcesactivities.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizzService {
    @Autowired
    private QuizzRepository quizzRepository;
    @Autowired
    private MyResourceRepository myResourceRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReinforceTopicRepository reinforceTopicRepository;
    @Autowired
    private AlternativeRepository alternativeRepository;
    @Transactional
    public List<QuizzDTO> getAll(){
        List<Quizz> quizzList = quizzRepository.findAll();
        List<QuizzDTO> quizzDTOList = quizzList.stream().map(c ->

                 QuizzDTO.builder()
                .id(c.getId())
                .name(c.getName())
                .nota((c.getNota()))
                .createdAt(c.getCreatedAt())
                .typeQuizz(TypeQuizzDTO.builder()
                        .id(c.getTypeQuizz().getId())
                        .name(c.getTypeQuizz().getName())
                        .build())
                .myResource(MyResourceDTO.builder()
                        .id(c.getMyResource().getId())
                        .name(c.getMyResource().getName())
                        .description(c.getMyResource().getDescription())
                        .topic(TopicDTO.builder()
                                .id(c.getMyResource().getTopic().getId())
                                .build())
                        .status(c.getMyResource().getStatus())
                        .createdAt(c.getMyResource().getCreatedAt())
                        .updatedAt(c.getMyResource().getUpdatedAt())
                        .build())
                .build()).collect(Collectors.toList());
        return quizzDTOList;
    }
    @Transactional
    public QuizzDTO getById(Integer id) {
        Quizz quizz=quizzRepository.findById(id).
                orElseThrow(() -> new ExpressionException("Cuestionario no encontrado con ID: " + id));

        return QuizzDTO.builder()
                .id(quizz.getId())
                .name(quizz.getName())
                .nota((quizz.getNota()))
                .createdAt(quizz.getCreatedAt())
                .typeQuizz(TypeQuizzDTO.builder()
                        .id(quizz.getTypeQuizz().getId())
                        .name(quizz.getTypeQuizz().getName())
                        .build())
                .myResource(MyResourceDTO.builder()
                        .id(quizz.getMyResource().getId())
                        .name(quizz.getMyResource().getName())
                        .description(quizz.getMyResource().getDescription())
                        .topic(TopicDTO.builder()
                                .id(quizz.getMyResource().getTopic().getId())
                                .build())
                        .status(quizz.getMyResource().getStatus())
                        .createdAt(quizz.getMyResource().getCreatedAt())
                        .updatedAt(quizz.getMyResource().getUpdatedAt())
                        .build())
                .build();

    }
    @Transactional
    public List<QuizzDTO> findByMyResourceId(Integer id){
    return quizzRepository.findByMyResourceId(id).stream().map(
            quizz->{
                return QuizzDTO.builder()
                        .id(quizz.getId())
                        .name(quizz.getName())
                        .nota((quizz.getNota()))
                        .createdAt(quizz.getCreatedAt())
                        .typeQuizz(TypeQuizzDTO.builder()
                                .id(quizz.getTypeQuizz().getId())
                                .name(quizz.getTypeQuizz().getName())
                                .build())
                        .myResource(MyResourceDTO.builder()
                                .id(quizz.getMyResource().getId())
                                .name(quizz.getMyResource().getName())
                                .description(quizz.getMyResource().getDescription())
                                .topic(TopicDTO.builder()
                                        .id(quizz.getMyResource().getTopic().getId())
                                        .build())
                                .status(quizz.getMyResource().getStatus())
                                .createdAt(quizz.getMyResource().getCreatedAt())
                                .updatedAt(quizz.getMyResource().getUpdatedAt())
                                .build())
                        .build();
            }).collect(Collectors.toList());

    }
    @Transactional
    public List<QuizzDTO> findByTypeQuizzId(Integer id){
        return quizzRepository.findByTypeQuizzId(id).stream().map(
                quizz->{
                    return QuizzDTO.builder()
                            .id(quizz.getId())
                            .name(quizz.getName())
                            .nota((quizz.getNota()))
                            .createdAt(quizz.getCreatedAt())
                            .typeQuizz(TypeQuizzDTO.builder()
                                    .id(quizz.getTypeQuizz().getId())
                                    .name(quizz.getTypeQuizz().getName())
                                    .build())
                            .myResource(MyResourceDTO.builder()
                                    .id(quizz.getMyResource().getId())
                                    .name(quizz.getMyResource().getName())
                                    .description(quizz.getMyResource().getDescription())
                                    .topic(TopicDTO.builder()
                                            .id(quizz.getMyResource().getTopic().getId())
                                            .build())
                                    .status(quizz.getMyResource().getStatus())
                                    .createdAt(quizz.getMyResource().getCreatedAt())
                                    .updatedAt(quizz.getMyResource().getUpdatedAt())
                                    .build())
                            .build();
                }).collect(Collectors.toList());

    }
    @Transactional
    public QuizzDTO save(QuizzDTO quizzDTO){

        Quizz quizz = new Quizz();
        quizz.setName(quizzDTO.getName());

        MyResource r = new MyResource();
        r.setId(quizzDTO.getMyResource().getId());
        TypeQuizzDTO tqdto=quizzDTO.getTypeQuizz();
        TypeQuizz tq= new TypeQuizz();
        tq.setId(tqdto.getId());
        if(tqdto.getId()==3){

        }
        quizz.setMyResource(r);
        quizz.setTypeQuizz(tq);
        quizz.setCreatedAt(quizzDTO.getCreatedAt());
        quizz.setNota(quizzDTO.getNota());
        quizzRepository.save(quizz);
        quizzDTO.setId(quizz.getId());
        return quizzDTO;
    }
    @Transactional
    public void update(QuizzDTO quizzDTO){
        quizzRepository.findById(quizzDTO.getId()).
                orElseThrow(() -> new RuntimeException("No se encontro el cuestionario a actualizar"));
        Quizz quizz = new Quizz();
        quizz.setId(quizzDTO.getId());
        quizz.setName(quizzDTO.getName());
        quizz.setNota(quizzDTO.getNota());
        MyResourceDTO rdto = quizzDTO.getMyResource();
        MyResource r = new MyResource();
        r.setId(rdto.getId());
        TypeQuizzDTO tqdto=quizzDTO.getTypeQuizz();
        TypeQuizz tq= new TypeQuizz();
        tq.setId(tqdto.getId());
        quizz.setMyResource(r);
        quizz.setTypeQuizz(tq);
        quizz.setCreatedAt(quizzDTO.getCreatedAt());
        quizzRepository.save(quizz);
    }
    @Transactional
    public void delete(Integer id){
        quizzRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + id));
        quizzRepository.deleteById(id);
    }
    @Transactional
    public void updateNota(Integer quizzId) {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));
        quizz.getQuestions().size();

        quizz.updateNota();

        quizzRepository.save(quizz);
    }
    @Transactional
    public Map<String, Object> getQuestionsGroupedByCompetencyAndLearningUnit(Integer topicId) {
        Topic topicF = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Tema no encontrado con ID: " + topicId));

        List<Question> questions = new ArrayList<>();
        Integer quizzId = null; // Variable para almacenar el ID del Quizz

        for (MyResource resource : topicF.getResources()) {
            for (Quizz quizz : resource.getQuizzes()) {
                if (quizz.getTypeQuizz().getId() == 2) { // Tipo "tema"
                    questions.addAll(quizz.getQuestions());
                    quizzId = quizz.getId(); // Obtener el ID del Quizz
                }
            }
        }

        Map<String, Map<String, Map<String, List<QuestionDTO>>>> groupedQuestions = new HashMap<>();

        for (Question question : questions) {
            Topic topic = question.getQuizz().getMyResource().getTopic();
            String competencyName = topic.getCompetence().getName();
            String learningUnitName = topic.getLearningUnit().getName();
            String topicName = topic.getName();

            groupedQuestions
                    .computeIfAbsent(competencyName, k -> new HashMap<>())
                    .computeIfAbsent(learningUnitName, k -> new HashMap<>())
                    .computeIfAbsent(topicName, k -> new ArrayList<>())
                    .add(convertToDto(question));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("quizzId", quizzId);
        response.put("groupedQuestions", groupedQuestions);

        return response;
    }
    @Transactional
    public Map<String, Object> getTotalNotaQuizz(Integer quizzId,Long userId,Integer topicId)  {

        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));


        List<Map<String, Object>> quizResults = new ArrayList<>();

                    if (quizz.getTypeQuizz().getId() == 2) {
                        Map<String, Object> quizResult = new HashMap<>();
                        quizResult.put("quizzId", quizz.getId());
                        quizResult.put("nota", quizz.getNota());

                        List<Map<String, Object>> incorrectQuestions = new ArrayList<>();
                        for (Question question : quizz.getQuestions()) {
                            System.out.println("nombre pregunta " + question.getName());
                            List<Alternative> incorrectAlternatives = question.getAlternatives().stream()
                                    .filter(Alternative::getIs_marked)
                                    .filter(alternative -> !alternative.getIs_answer())
                                    .collect(Collectors.toList());

                            for (Alternative incorrectAlternative : incorrectAlternatives) {
                                System.out.println("Alternativas incorrectas:  " + incorrectAlternative.getValue());
                                Map<String, Object> incorrectQuestionInfo = new HashMap<>();
                                incorrectQuestionInfo.put("question", convertToDto(question));
                                incorrectQuestionInfo.put("incorrectAlternative", convertToDto(incorrectAlternative));


                                Optional<Alternative> correctAlternative = question.getAlternatives().stream()
                                        .filter(Alternative::getIs_answer)
                                        .filter(alternative -> !alternative.getIs_marked())
                                        .findFirst();

                                correctAlternative.ifPresent(alt -> {
                                    incorrectQuestionInfo.put("correctAlternative", convertToDto(alt));
                                });

                                incorrectQuestions.add(incorrectQuestionInfo);

                            }
                        }
                        quizResult.put("incorrectQuestions", incorrectQuestions);
                        quizResults.add(quizResult);
                    }



        Map<String, Object> result = new HashMap<>();
        result.put("quizResults", quizResults);

        if(quizz.getNota()>=11){
            List<ReinforceTopic> rtl=reinforceTopicRepository.findByUserId(userId);
            for(ReinforceTopic rt:rtl){
                if(rt.getTopic().getId()==topicId){
                    rt.setEstado(true);
                    reinforceTopicRepository.save(rt);
                }
            }
        }

        quizz.setNota(0.0);


        return result;



    }
    @Transactional
    public Map<String, Map<String, Map<String, List<QuestionDTO>>>> getQuestionsForCourseGroupedByCompetencyAndLearningUnit(Integer quizzId, Integer courseId) {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));

        if (!"curso".equals(quizz.getTypeQuizz().getName())) {
            throw new RuntimeException("Este método solo es aplicable para cuestionarios de tipo 'PorCurso'");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + courseId));

        List<Topic> topics = topicRepository.findAllByCourse(course);
        Map<String, Map<String, Map<String, List<QuestionDTO>>>> groupedQuestions = new HashMap<>();

        for (Topic topic : topics) {
            for (MyResource resource : topic.getResources()) {
                for (Quizz relatedQuizz : resource.getQuizzes()) {
                    if (relatedQuizz.getTypeQuizz().getId() == 3) {
                        for (Question question : relatedQuizz.getQuestions()) {
                            String competencyName = topic.getCompetence().getName();
                            String learningUnitName = topic.getLearningUnit().getName();
                            String topicName = topic.getName();

                            groupedQuestions
                                    .computeIfAbsent(competencyName, k -> new HashMap<>())
                                    .computeIfAbsent(learningUnitName, k -> new HashMap<>())
                                    .computeIfAbsent(topicName, k -> new ArrayList<>())
                                    .add(convertToDto(question));
                        }
                    }
                }
            }
        }

        return groupedQuestions;
    }

    @Transactional
    public List<QuestionDTO> getQuestionsWithAlternativesForQuizz(Integer quizzId) {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));

        if (quizz.getTypeQuizz().getId() != 1 && quizz.getTypeQuizz().getId() != 4) {
            throw new RuntimeException("Este método solo es aplicable para cuestionarios de tipo 'Independiente' o 'PorTema'");
        }

        List<Question> questions = questionRepository.findByQuizzId(quizzId);

        return questions.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Transactional
    public List<AlternativeDTO> getMarkedAlternativesForQuizz(Integer quizzId) {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));

        List<Question> questions = questionRepository.findByQuizzId(quizzId);
        List<Alternative> markedAlternatives = new ArrayList<>();

        for (Question question : questions) {
            markedAlternatives.addAll(question.getAlternatives().stream()
                    .filter(Alternative::getIs_marked)
                    .collect(Collectors.toList()));
        }
        // Ordenar las alternativas por el campo 'id' en orden ascendente
        markedAlternatives.sort(Comparator.comparingInt(Alternative::getId));
        return markedAlternatives.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public Map<String, Object> getTotalNotaForCourse(Integer quizzId, Integer courseId,Long userId) {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));

        if (!"curso".equals(quizz.getTypeQuizz().getName())) {
            throw new RuntimeException("Este método solo es aplicable para cuestionarios de tipo 'PorCurso'");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + courseId));

        List<Map<String, Object>> quizResults = new ArrayList<>();
        Set<Topic> reinforceTopics = new HashSet<>();
        List<Topic> topics = topicRepository.findAllByCourse(course);


        for (Topic topic : topics) {
            for (MyResource resource : topic.getResources()) {
                for (Quizz relatedQuizz : resource.getQuizzes()) {
                    if (relatedQuizz.getTypeQuizz().getId() == 3 && relatedQuizz.getNota() <=11) {
                        Map<String, Object> quizResult = new HashMap<>();
                        quizResult.put("quizzId", relatedQuizz.getId());
                        quizResult.put("nota", relatedQuizz.getNota());

                        List<Map<String, Object>> incorrectQuestions = new ArrayList<>();
                        for (Question question : relatedQuizz.getQuestions()) {
                            System.out.println("nombre pregunta " + question.getName());
                            List<Alternative> incorrectAlternatives = question.getAlternatives().stream()
                                    .filter(Alternative::getIs_marked)
                                    .filter(alternative -> !alternative.getIs_answer())
                                    .collect(Collectors.toList());

                            for (Alternative incorrectAlternative : incorrectAlternatives) {
                                System.out.println("Alternativas incorrectas:  " + incorrectAlternative.getValue());
                                Map<String, Object> incorrectQuestionInfo = new HashMap<>();
                                incorrectQuestionInfo.put("question", convertToDto(question));
                                incorrectQuestionInfo.put("incorrectAlternative", convertToDto(incorrectAlternative));
                                incorrectQuestionInfo.put("topic", convertToDto(topic));

                                Optional<Alternative> correctAlternative = question.getAlternatives().stream()
                                        .filter(Alternative::getIs_answer)
                                        .filter(alternative -> !alternative.getIs_marked())
                                        .findFirst();

                                correctAlternative.ifPresent(alt -> {
                                    incorrectQuestionInfo.put("correctAlternative", convertToDto(alt));
                                    incorrectQuestionInfo.put("topic", convertToDto(topic));
                                });

                                incorrectQuestions.add(incorrectQuestionInfo);
                                reinforceTopics.add(topic);
                            }
                        }
                        quizResult.put("incorrectQuestions", incorrectQuestions);
                        quizResults.add(quizResult);
                    }
                }
            }
        }


        Map<String, Object> result = new HashMap<>();
        result.put("quizResults", quizResults);

        for (Topic topic : topics) {
            for (MyResource resource : topic.getResources()) {
                for (Quizz relatedQuizz : resource.getQuizzes()) {
                    if (relatedQuizz.getTypeQuizz().getId() == 3){
                        relatedQuizz.setNota(0.0);
                    }
                }
            }
        }


        // Borrar registros existentes de ReinforceTopic para el userId proporcionado
        reinforceTopicRepository.deleteByUserId(userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            for (Topic topic : reinforceTopics) {
                ReinforceTopic reinforceTopic = new ReinforceTopic();
                reinforceTopic.setUser(user.orElseThrow());
                reinforceTopic.setTopic(topic);
                reinforceTopic.setQuizz(quizz);
                reinforceTopicRepository.save(reinforceTopic);
            }
        }


        return result;
    }
    @Transactional
    public void unmarkAlternativesForQuizz(Integer quizzId) {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));

        for (Question question : quizz.getQuestions()) {
            for (Alternative alternative : question.getAlternatives()) {
                if (alternative.getIs_marked()) {
                    alternative.setIs_marked(false);
                    alternativeRepository.save(alternative);
                }
            }
        }
    }
    @Transactional
    public void unmarkAlternativesForCourseQuizz(Integer quizzId, Integer courseId) {
        Quizz quizz = quizzRepository.findById(quizzId)
                .orElseThrow(() -> new RuntimeException("Cuestionario no encontrado con ID: " + quizzId));

        if (!"curso".equals(quizz.getTypeQuizz().getName())) {
            throw new RuntimeException("Este método solo es aplicable para cuestionarios de tipo 'PorCurso'");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + courseId));

        List<Topic> topics = topicRepository.findAllByCourse(course);


        for (Topic topic : topics) {
            for (MyResource resource : topic.getResources()) {
                for (Quizz relatedQuizz : resource.getQuizzes()) {
                    if (relatedQuizz.getTypeQuizz().getId() == 3) {
                        for (Question question : relatedQuizz.getQuestions()) {
                            for (Alternative alternative : question.getAlternatives()) {
                                if (alternative.getIs_marked()) {
                                    alternative.setIs_marked(false);
                                    alternativeRepository.save(alternative);
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    private QuestionDTO convertToDto(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .name(question.getName())
                .points(question.getPoints())
                .correctAnswer(question.getCorrectAnswer())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .alternatives(question.getAlternatives().stream().map(this::convertToDto).collect(Collectors.toList()))
                .build();
    }

    private AlternativeDTO convertToDto(Alternative alternative) {
        return AlternativeDTO.builder()
                .id(alternative.getId())
                .value(alternative.getValue())
                .is_answer(alternative.getIs_answer())
                .is_marked(alternative.getIs_marked())
                .build();
    }
    private TopicDTO convertToDto(Topic topic) {
        return TopicDTO.builder()
                .id(topic.getId())
                .name(topic.getName())
                .competence(topic.getCompetence())
                .learningUnit(topic.getLearningUnit())
                .build();
    }


}
