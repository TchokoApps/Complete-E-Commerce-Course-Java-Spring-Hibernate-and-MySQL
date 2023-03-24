package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Name cannot be null")
    @Size(max = 40, message = "Name cannot exceed 40 characters")
    @Column(length = 40, nullable = false, unique = true)
    private String name;

    @Size(max = 150, message = "Description cannot exceed 150 characters")
    @Column(length = 150, nullable = false)
    private String description;

    @Override
    public String toString() {
        return name;
    }
}
