package com.example.resourcesactivities.model;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;

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
    @Size(min = 4, max = 12)
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    private String nombres;

    private String apellidos;
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


}
