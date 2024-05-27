package com.example.resourcesactivities.dto;

import java.time.LocalDate;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private LocalDate fechaNacimiento;
    private boolean docente;
    private boolean padrefam;

    public UserDTO(Long id, String username, String email ,  boolean docente , boolean padrefam) {
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
}
