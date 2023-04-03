package com.tchokoapps.springboot.ecommerce.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
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
    @Size(min = 3, max = 45, message = "Name must be between 3 and 45 characters long.")
    @Column(nullable = false, unique = true)
    private String name;

    @Size(min = 3, max = 45, message = "Photo must be between 3 and 45 characters long.")
    @Column(unique = true)
    private String photo;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdTime;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime updatedTime;

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
