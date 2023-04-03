package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @Size(min = 3, max = 256, message = "Name must be between 3 and 256 characters long.")
    @Column(unique = true, nullable = false)
    private String name;

    @Size(min = 3, max = 45, message = "Photo must be between 3 and 45 characters long.")
    private String photo;

    private boolean enabled;

    @Size(min = 3, max = 45, message = "Alias must be between 3 and 45 characters long.")
    private String alias;

    @Size(min = 3, max = 500, message = "Short Description must be between 3 and 45 characters long.")
    private String shortDescription;

    @Size(min = 3, max = 20000, message = "Full Description must be between 3 and 20000 characters long.")
    private String fullDescription;

    private String mainImage;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductImage> productImages = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
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

    public void addExtraImage(String imageName) {
        ProductImage productImage = ProductImage.builder().name(name).product(this).build();
        this.productImages.add(productImage);
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
