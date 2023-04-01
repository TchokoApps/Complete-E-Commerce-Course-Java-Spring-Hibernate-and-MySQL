package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Table(name = "brands")
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 45)
    @Column(nullable = false, unique = true)
    private String name;

    @Size(max = 45)
    @Column(unique = true)
    private String photo;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


    public Brand() {
        this.createdTime = LocalDateTime.now();
    }

    public static class BrandBuilder {
        public BrandBuilder() {
            createdTime(LocalDateTime.now());
        }
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
