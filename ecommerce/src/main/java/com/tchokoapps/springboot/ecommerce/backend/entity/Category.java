package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Size(min = 3, max = 128, message = "Name must be between 3 and 128 characters long.")
    @Column(nullable = false, unique = true)
    private String name;

    @Size(min = 3, max = 64, message = "Alias must be between 3 and 64 characters long.")
    private String alias;

    @Size(min = 3, max = 128, message = "Photo must be between 3 and 128 characters long.")
    private String photo;

    @NotNull
    private boolean enabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Category> children = new HashSet<>();

    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    public Category() {
        this.enabled = true;
    }

    @Transient
    public String getImagePath() {
        if (id == null || photo == null) {
            return "/upload/no_image.jpg";
        }
        return "/photos/" + photo;
    }

    @Transient
    public String getParentName() {
        if (parent != null) {
            return parent.getName();
        }

        return "";
    }

    public static class CategoryBuilder {
        public CategoryBuilder() {
            enabled(true);
        }
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
