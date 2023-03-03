package com.tchokoapps.springboot.ecommerce.backend.entity;

import com.tchokoapps.springboot.ecommerce.common.validator.UniqueEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @UniqueEmail
    @NotNull(message = "Object cannot be null")
    @Size(max = 128, message = "Object cannot exceed 128 characters")
    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @NotNull(message = "Object cannot be null")
    @Size(max = 64, message = "Object cannot exceed 64 characters")
    @Column(length = 64, nullable = false)
    private String password;

    @NotNull(message = "Object cannot be null")
    @Size(max = 45, message = "Object cannot exceed 45 characters")
    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @NotNull(message = "Object cannot be null")
    @Size(max = 45, message = "Object cannot exceed 45 characters")
    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Size(max = 64, message = "Photos cannot exceed 64 characters")
    @Column(length = 64)
    private String photos;

    private boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
