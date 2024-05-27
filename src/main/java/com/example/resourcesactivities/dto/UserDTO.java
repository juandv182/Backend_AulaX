package com.example.resourcesactivities.dto;

import java.time.LocalDate;
import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDate fechaNacimiento;
    private boolean docente;
    private boolean padrefam;
    private List<UserDTO> hijos;
    private UserDTO padreFamilia;

    public UserDTO(Long id, String username, String email, boolean docente, boolean padrefam) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.docente = docente;
        this.padrefam = padrefam;
    }

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public boolean isDocente() {
        return docente;
    }

    public void setDocente(boolean docente) {
        this.docente = docente;
    }

    public boolean isPadrefam() {
        return padrefam;
    }

    public void setPadrefam(boolean padrefam) {
        this.padrefam = padrefam;
    }

    public List<UserDTO> getHijos() {
        return hijos;
    }

    public void setHijos(List<UserDTO> hijos) {
        this.hijos = hijos;
    }

    public UserDTO getPadreFamilia() {
        return padreFamilia;
    }

    public void setPadreFamilia(UserDTO padreFamilia) {
        this.padreFamilia = padreFamilia;
    }
}