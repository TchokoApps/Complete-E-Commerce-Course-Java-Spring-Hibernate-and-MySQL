package com.tchokoapps.springboot.ecommerce.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 256, message = "Name must be between 3 and 256 characters long.")
    @Column(unique = true, nullable = false)
    private String name;

    private boolean enabled;

    @Size(min = 3, max = 256, message = "Alias must be between 3 and 256 characters long.")
    private String alias;

    @Size(min = 3, max = 1000, message = "Short Description must be between 3 and 1000 characters long.")
    private String shortDescription;

    @Size(min = 3, max = 20000, message = "Full Description must be between 3 and 20000 characters long.")
    private String fullDescription;

    @Size(min = 3, max = 256, message = "Main Image must be between 3 and 256 characters long.")
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductDetail> productDetails = new HashSet<>();

    public Product() {
        this.enabled = true;
        this.inStock = true;
    }

    @Transient
    public void addExtraImage(String imageName) {
        ProductImage productImage = ProductImage.builder()
                .name(imageName)
                .product(this)
                .build();
        this.productImages.add(productImage);
    }

    @Transient
    public void addProductDetail(String name, String value) {
        ProductDetail productDetail = ProductDetail.builder()
                .name(name)
                .productDetailValue(value)
                .product(this)
                .build();
        productDetails.add(productDetail);
    }

    @Transient
    public void removeProductDetail(ProductDetail productDetail) {
        productDetails.remove(productDetail);
    }

    @Transient
    public String obtainMainImagePath() {

        if (id == null || StringUtils.isBlank(mainImage)) {
            return "/upload/no_image.jpg";
        }
        return "/photos/" + mainImage;
    }

    @Transient
    public List<String> obtainMoreImages() {
        List<String> moreImages = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            moreImages.add(productImage.getName());
        }
        return moreImages;
    }

    public static class ProductBuilder {
        public ProductBuilder() {
            enabled(true);
            inStock(true);
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
