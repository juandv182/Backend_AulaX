package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.UserDTO;
import com.example.resourcesactivities.model.User;
import com.example.resourcesactivities.request.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

        List<UserDTO> findAll();

        Optional<UserDTO> findById(Long id);

        UserDTO save(User user);
        Optional<UserDTO> update(UserRequest user, Long id);
        UserDTO findByUsername(String username);

        void remove(Long id);


}
