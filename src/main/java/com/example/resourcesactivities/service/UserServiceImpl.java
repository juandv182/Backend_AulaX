package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.UserDTO;
import com.example.resourcesactivities.dto.mapper.DtoMapperUser;
import com.example.resourcesactivities.model.Course;
import com.example.resourcesactivities.model.IUser;
import com.example.resourcesactivities.model.Role;
import com.example.resourcesactivities.model.User;
import com.example.resourcesactivities.repository.CourseRepository;
import com.example.resourcesactivities.repository.RoleRepository;
import com.example.resourcesactivities.repository.UserRepository;
import com.example.resourcesactivities.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = (List<User>) repository.findAll();
        return users
                .stream()
                .map(u -> DtoMapperUser.builder().setUser(u).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        return repository.findById(id).map(u -> DtoMapperUser
                .builder()
                .setUser(u)
                .build());

    }

    @Override
    @Transactional
    public UserDTO save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(getRoles( user));

        if (user.getFechaNacimiento() == null || user.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha de nacimiento no válida");
        }

        // Asignar todos los cursos al nuevo usuario
        List<Course> allCourses = courseRepository.findAll();
        for(Course c:allCourses){
            c.getUsers().add(user);
        }
        return DtoMapperUser.builder().setUser(repository.save(user)).build();
    }


    @Override
    @Transactional
    public Optional<UserDTO> update(UserRequest user, Long id) {
        Optional<User> o = repository.findById(id);
        User userOptional = null;
        if (o.isPresent()) {
            User userDb = o.orElseThrow();
            userDb.setRoles(getRoles(user));
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            userOptional = repository.save(userDb);
        }
        return Optional.ofNullable(DtoMapperUser.builder().setUser(userOptional).build());
    }

    @Override
    @Transactional
    public UserDTO findByUsername(String username) {
        Optional<User> u=repository.findByUsername(username);

        UserDTO userDto = DtoMapperUser.builder().setUser(u.orElseThrow()).build();

        return  userDto;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateUserLoginTimes(Long userId, LocalDateTime loginTime) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.updateLoginTimes(loginTime);
        repository.save(user);
    }
    @Override
    public void updatePreferenciasAprendizaje(Long userId, String preferencias) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.updatePrefenrenciasAprendizaje(preferencias);
        repository.save(user);
    }

    @Override
    public void updateUserLogoutTimes(Long userId, LocalDateTime logoutTime) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.updateLogoutTime(logoutTime);
        repository.save(user);
    }
    public String getUserTotalTimeLoggedInReadable(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return user.getTotalTimeLoggedInReadable();
    }

    @Override
    public List<UserDTO> getAllUsersTotalTimeLoggedIn() {
        List<User> users = (List<User>) repository.findAll();
        return users.stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setTotalTimeLoggedIn(user.getTotalTimeLoggedIn());
            return userDTO;
        }).collect(Collectors.toList());
    }

    private List<Role> getRoles(IUser user) {
        Optional<Role> ou = roleRepository.findByName("ROLE_ALUMNO");

        List<Role> roles = new ArrayList<>();
        if (!user.isDocente() && !user.isPadrefam()) {
            roles.add(ou.orElseThrow());
        }

        if (user.isDocente()) {
            Optional<Role> oa = roleRepository.findByName("ROLE_DOCENTE");
            if (oa.isPresent()) {
                roles.add(oa.orElseThrow());
            }
        }
        if (user.isPadrefam()) {
            Optional<Role> op = roleRepository.findByName("ROLE_PADREFAM");
            if (op.isPresent()) {
                roles.add(op.orElseThrow());
            }
        }
        return roles;
    }
}