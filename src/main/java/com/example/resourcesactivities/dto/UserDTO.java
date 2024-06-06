package com.example.resourcesactivities.dto;

import java.time.LocalDate;
import java.util.List;

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

    public UserDTO(Long id, String username, String email,LocalDate fechaNacimiento,
                   boolean docente, boolean padrefam, Long id_hijo,String nombres,String apellidos,
                   boolean genero) {
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

    public Long getId_hijo() {
        return id_hijo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean isGenero() {
        return genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    public void setId_hijo(Long id_hijo) {
        this.id_hijo = id_hijo;
    }
}