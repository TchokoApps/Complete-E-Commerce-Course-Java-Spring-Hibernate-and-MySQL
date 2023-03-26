package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(length = 128, nullable = false, unique = true)
    private String name;
    @Column(length = 64)
    private String alias;
    @Column(length = 128)
    private String photo;
    private boolean enabled;
    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Category parent;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Category> children = new HashSet<>();

    public Category() {

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
                '}';
    }
}
