package com.example.resourcesactivities.model;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.Duration;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="users")
public class User implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 16)
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    private String nombres;

    private String apellidos;
    private String preferenciaAprendizaje;
    private boolean genero;
    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private LocalDate fechaNacimiento;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"})})
    private List<Role> roles;

    private boolean docente;

    private boolean padrefam;

    private Long id_hijo;

    private boolean isFirstLogin=true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime firstLogin;
    private LocalDateTime lastLogin;
    private Long totalTimeLoggedIn = 0L;
    public void updateLoginTimes(LocalDateTime loginTime) {
        if (this.isFirstLogin) {
            this.firstLogin = loginTime;
        }
        if (this.lastLogin != null) {
            this.totalTimeLoggedIn = java.time.Duration.between(this.lastLogin, loginTime).getSeconds();
        }
        this.lastLogin = loginTime;
    }
    public void updateLogoutTime(LocalDateTime logoutTime) {
        if (this.lastLogin != null) {
            this.totalTimeLoggedIn = java.time.Duration.between(this.lastLogin, logoutTime).getSeconds();
        }
        this.lastLogin = logoutTime;
        if (this.isFirstLogin) {
            this.isFirstLogin = false;
        }
    }
    public String getTotalTimeLoggedInReadable() {
        long hours = this.totalTimeLoggedIn / 3600;
        long minutes = (this.totalTimeLoggedIn % 3600) / 60;
        long seconds = this.totalTimeLoggedIn % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}
