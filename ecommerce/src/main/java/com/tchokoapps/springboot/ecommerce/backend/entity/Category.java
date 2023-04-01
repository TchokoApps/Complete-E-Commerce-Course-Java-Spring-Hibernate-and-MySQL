package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 128)
    @Column(nullable = false, unique = true)
    private String name;

    @Size(max = 64)
    private String alias;

    @Size(max = 128)
    private String photo;

    @NotNull
    private boolean enabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Category> children = new HashSet<>();

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public Category() {
        this.createdTime = LocalDateTime.now();
        this.enabled = true;
    }

    public static class CategoryBuilder {
        public CategoryBuilder() {
            createdTime(LocalDateTime.now());
            enabled(true);
        }
    }

    @Transient
    public String getParentName() {
        if (parent != null) {
            return parent.getName();
        }

        return "";
    }

    @Transient
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", photo='" + photo + '\'' +
                ", enabled=" + enabled +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
