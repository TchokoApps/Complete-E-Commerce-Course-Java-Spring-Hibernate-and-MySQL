package com.tchokoapps.springboot.ecommerce.backend.service.impl;

import com.tchokoapps.springboot.ecommerce.backend.entity.Product;
import com.tchokoapps.springboot.ecommerce.backend.entity.ProductImage;
import com.tchokoapps.springboot.ecommerce.backend.exception.ProductNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.repository.ProductImageRepository;
import com.tchokoapps.springboot.ecommerce.backend.repository.ProductRepository;
import com.tchokoapps.springboot.ecommerce.backend.service.ProductImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ProductImageServiceImpl implements ProductImageService {

    private ProductRepository productRepository;
    private ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public void deleteProductImage(Integer productId, Integer productImageId) {

        Objects.requireNonNull(productId, "productId cannot be null");
        Objects.requireNonNull(productImageId, "productImageId cannot be null");

        Optional<Product> productOptional = productRepository.findById(productId);
        Optional<ProductImage> productImageOptional = productImageRepository.findById(productImageId);

        if (productOptional.isPresent() && productImageOptional.isPresent()) {
            Product product = productOptional.get();
            ProductImage productImage = productImageOptional.get();
            product.removeProductImage(productImage);
            productRepository.save(product);
            productImageRepository.delete(productImage);
            log.info("productImage with id {} deleted successfully", productImageId);
        }
    }

    @Override
    @Transactional
    public void deleteAllProductImagesByProductId(Integer productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ProductNotFoundException(String.format("Product with id %s cannot be found", productId)));

        product.removeAllProductImages();

        productImageRepository.deleteAllByProductId(productId);
    }


}
