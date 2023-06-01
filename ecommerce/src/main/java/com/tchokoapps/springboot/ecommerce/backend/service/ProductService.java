package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Product;
import com.tchokoapps.springboot.ecommerce.backend.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(Integer id) throws ProductNotFoundException;

    Product save(Product product);

    Product findByName(String name) throws ProductNotFoundException;

    void deleteProduct(Integer id);
}
