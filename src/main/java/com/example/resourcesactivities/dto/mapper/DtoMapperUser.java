package com.example.resourcesactivities.dto.mapper;

import com.example.resourcesactivities.dto.UserDTO;
import com.example.resourcesactivities.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapperUser {
    private User user;

    private DtoMapperUser() {
    }

    public static DtoMapperUser builder() {
        return new DtoMapperUser();
    }

    public DtoMapperUser setUser(User user) {
        this.user = user;
        return this;
    }

    public UserDTO build() {
        if (user == null) {
            throw new RuntimeException("Debe pasar el entity user!");
        }
        boolean isDocente = user.getRoles().stream().anyMatch(r -> "ROLE_DOCENTE".equals(r.getName()));
        boolean isPadrefam = user.getRoles().stream().anyMatch(r -> "ROLE_PADREFAM".equals(r.getName()));
        UserDTO userDTO = new UserDTO(
                this.user.getId(),
                this.user.getUsername(),
                this.user.getEmail(),
                this.user.getFechaNacimiento(),
                isDocente,
                isPadrefam,
                this.user.getId_hijo(),
                this.user.getNombres(),
                this.user.getApellidos(),
                this.user.isGenero()

        );


        return userDTO;
    }
}
