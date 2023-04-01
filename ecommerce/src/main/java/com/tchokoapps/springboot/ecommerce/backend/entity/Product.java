package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 256)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(max = 256)
    private String photo;

    private boolean enabled;

    @Size(max = 256)
    private String alias;

    @Size(max = 500)
    private String shortDescription;

    @Size(max = 2000)
    private String fullDescription;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @NotNull
    private boolean inStock;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal cost;

    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @DecimalMax(value = "100.0")
    private BigDecimal discountPercent;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal length;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal width;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal height;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;


    public Product() {
        this.createdTime = LocalDateTime.now();
        this.inStock = true;
        this.enabled = true;
    }

    public static class ProductBuilder {
        public ProductBuilder() {
            createdTime(LocalDateTime.now());
            inStock(true);
            enabled(true);
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", inStock=" + inStock +
                ", cost=" + cost +
                ", price=" + price +
                ", discountPercent=" + discountPercent +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }
}
