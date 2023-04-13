package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

    @Query("DELETE FROM ProductImage pi WHERE pi.product.id = :productId")
    @Modifying
    void deleteAllByProductId(@Param("productId") Integer productId);
}
