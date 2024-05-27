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

        List<UserDTO> hijos = user.getHijos() != null ? user.getHijos().stream()
                .map(hijo -> DtoMapperUser.builder().setUser(hijo).build())
                .collect(Collectors.toList()) : null;

        UserDTO padreFamilia = user.getPadreFamilia() != null ?
                DtoMapperUser.builder().setUser(user.getPadreFamilia()).build() : null;

        UserDTO userDTO = new UserDTO(
                this.user.getId(),
                this.user.getUsername(),
                this.user.getEmail(),
                this.user.isDocente(),
                this.user.isPadrefam()
        );

        userDTO.setHijos(hijos);
        userDTO.setPadreFamilia(padreFamilia);

        return userDTO;
    }
}
