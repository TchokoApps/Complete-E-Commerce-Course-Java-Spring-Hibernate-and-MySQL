package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Product;
import com.tchokoapps.springboot.ecommerce.backend.entity.ProductImage;
import com.tchokoapps.springboot.ecommerce.backend.exception.ProductNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.repository.ProductImageRepository;
import com.tchokoapps.springboot.ecommerce.backend.repository.ProductRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductImageServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductImageService productImageService;

    private Product productSaved;

    @BeforeEach
    public void setUp() {

        String text = RandomStringUtils.randomAlphanumeric(20);

        Product product = Product.builder()
                .name(text)
                .alias(text)
                .shortDescription(text)
                .fullDescription(text)
                .productImages(new HashSet<>())
                .inStock(true)
                .cost(BigDecimal.valueOf(700))
                .price(BigDecimal.valueOf(999))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(5.78))
                .width(BigDecimal.valueOf(2.82))
                .weight(BigDecimal.valueOf(2.82))
                .height(BigDecimal.valueOf(0.3))
                .weight(BigDecimal.valueOf(0.29))
                .build();

        product.addProductImage("productImage1");
        product.addProductImage("productImage2");
        product.addProductImage("productImage3");

        productSaved = productRepository.save(product);
    }

    @Test
    public void testCreateProductImages() {
        Set<ProductImage> productImages = productSaved.getProductImages();

        assertThat(productImages)
                .hasSize(3)
                .extracting(ProductImage::getId)
                .allSatisfy(id -> assertThat(id).isNotNull());
    }

    @Test
    public void testDeleteAllProductImagesByProductId() throws ProductNotFoundException {

        Set<ProductImage> productImages = productSaved.getProductImages();

        productImageService.deleteAllProductImagesByProductId(productSaved.getId());

        productImages.forEach(productImage -> {
            Optional<ProductImage> productImageOptional = productImageRepository.findById(productImage.getId());
            assertThat(productImageOptional.isEmpty()).isTrue();
        });

        List<ProductImage> productImagesFound = productImageRepository.findAllByProductId(productSaved.getId());

        assertThat(productImagesFound).hasSize(0);
    }

}