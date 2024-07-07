package com.example.resourcesactivities.request;

import com.example.resourcesactivities.model.IUser;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class UserRequest implements IUser {
    @NotBlank
    @Size(min = 4, max = 12)
    private String username;

    @NotEmpty
    @Email
    private String email;

    private String password;

    private boolean docente;
    private boolean padrefam;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public boolean isDocente() {
        return docente;
    }
    public void setDocente(boolean docente) {
        this.docente = docente;
    }
    @Override
    public boolean isPadrefam() {
        return padrefam;
    }
    public void setPadrefam(boolean padrefam) {
        this.padrefam = padrefam;
    }
}
