package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.exception.ProductNotFoundException;

import javax.transaction.Transactional;

public interface ProductImageService {
    @Transactional
    void deleteProductImage(Integer productId, Integer productImageId);

    @Transactional
    void deleteAllProductImagesByProductId(Integer productId) throws ProductNotFoundException;
}
