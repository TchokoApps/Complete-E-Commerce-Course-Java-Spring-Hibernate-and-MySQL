package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_details")
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 256, message = "Name must be between 3 and 256 characters long.")
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(min = 3, max = 256, message = "Value must be between 3 and 256 characters long.")
    private String productDetailValue;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @CreationTimestamp
    private LocalDateTime updatedTime;

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productDetailValue='" + productDetailValue + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
