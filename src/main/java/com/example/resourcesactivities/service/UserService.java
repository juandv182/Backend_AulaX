package com.example.resourcesactivities.service;

import com.example.resourcesactivities.dto.UserDTO;
import com.example.resourcesactivities.model.User;
import com.example.resourcesactivities.request.UserRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {

        List<UserDTO> findAll();

        Optional<UserDTO> findById(Long id);

        UserDTO save(User user);
        Optional<UserDTO> update(UserRequest user, Long id);
        UserDTO findByUsername(String username);

        void remove(Long id);
        void  updateUserLoginTimes(Long userId, LocalDateTime loginTime);
        void  updateUserLogoutTimes(Long userId, LocalDateTime logoutTime);
        void updatePreferenciasAprendizaje(Long userId, String preferencias);
        String getUserTotalTimeLoggedInReadable(Long userId);


}
