package com.example.resourcesactivities.controller;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.resourcesactivities.dto.UserDTO;
import com.example.resourcesactivities.model.User;
import com.example.resourcesactivities.request.UserRequest;
import com.example.resourcesactivities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDTO> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<UserDTO> userOptionl = service.findById(id);

        if (userOptionl.isPresent()) {
            return ResponseEntity.ok(userOptionl.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> showByUsername(@PathVariable String username) {
        UserDTO userDTO = service.findByUsername(username);

        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/{userId}/total-time")
    public ResponseEntity<String> getTotalTimeLoggedIn(@PathVariable Long userId) {
        String totalTimeLoggedIn = service.getUserTotalTimeLoggedInReadable(userId);
        return ResponseEntity.ok(totalTimeLoggedIn);
    }
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }
    @PostMapping("/{userId}/login")
    public ResponseEntity<Void> updateLoginTimes(@PathVariable Long userId) {
        LocalDateTime loginTime = LocalDateTime.now();
        service.updateUserLoginTimes(userId, loginTime);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{userId}/logout")
    public ResponseEntity<Void> updateLogoutTimes(@PathVariable Long userId) {
        LocalDateTime logoutTime = LocalDateTime.now();
        service.updateUserLogoutTimes(userId, logoutTime);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{userId}/user/{preferencia}/updatePreferenciasAprendizaje")
    public ResponseEntity<Void> updateLogoutTimes(@PathVariable Long userId,@PathVariable String preferencia ){
        service.updatePreferenciasAprendizaje(userId, preferencia);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody UserRequest user, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<UserDTO> o = service.update(user, id);

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<UserDTO> o = service.findById(id);

        if (o.isPresent()) {
            service.remove(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
