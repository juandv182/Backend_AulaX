package com.example.resourcesactivities.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String nombres;

    private String apellidos;
    private boolean genero;
    private String email;
    private LocalDate fechaNacimiento;
    private boolean docente;
    private boolean padrefam;
    private Long id_hijo;

    private  LocalDateTime firstLogin;
    private LocalDateTime lastLogin;
    private Long  totalTimeLoggedIn;
    private String preferenciaAprendizaje;
    private boolean esPrimerLoguin;

    public UserDTO(Long id, String username, String email, LocalDate fechaNacimiento,
                   boolean docente, boolean padrefam, Long id_hijo, String nombres, String apellidos,
                   boolean genero, String preferenciaAprendizaje,boolean esPrimerLoguin,LocalDateTime firstLogin,
                   LocalDateTime lastLogin, Long  totalTimeLoggedIn) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.docente = docente;
        this.padrefam = padrefam;
        this.id_hijo = id_hijo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.esPrimerLoguin=esPrimerLoguin;
        this.firstLogin = firstLogin;
        this.lastLogin =lastLogin;
        this.totalTimeLoggedIn = totalTimeLoggedIn;
        this.preferenciaAprendizaje = preferenciaAprendizaje;


    }

    public UserDTO() {
    }


}