package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @NotNull(message = "Email cannot be null")
    @Size(min = 8, message = "Email cannot be less than 8 characters")
    @Size(max = 128, message = "Email cannot exceed 128 characters")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password cannot be less than 8 characters")
    @Size(max = 64, message = "Password cannot exceed 64 characters")
    @Column(nullable = false)
    private String password;

    private String confirmPassword;

    @NotNull(message = "First Name cannot be null")
    @Size(max = 45, message = "First Name cannot exceed 45 characters")
    @Column(nullable = false)
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @Size(max = 45, message = "Last Name cannot exceed 45 characters")
    @Column(nullable = false)
    private String lastName;

    @Size(max = 64, message = "Photo cannot exceed 64 characters")
    private String photo;

    private boolean enabled;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
        this.createdTime = LocalDateTime.now();
        this.enabled = true;
    }

    public static class UserBuilder {
        public UserBuilder() {
            createdTime(LocalDateTime.now());
            enabled(true);
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photo='" + photo + '\'' +
                ", enabled=" + enabled +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
