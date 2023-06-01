package com.tchokoapps.springboot.ecommerce.backend.service.impl;

import com.tchokoapps.springboot.ecommerce.backend.entity.Product;
import com.tchokoapps.springboot.ecommerce.backend.exception.ProductNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.repository.ProductRepository;
import com.tchokoapps.springboot.ecommerce.backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Integer id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException(String.format("Product with id = %s cannot be found", id)));
    }

    @Override
    public Product save(Product product) {
        Objects.requireNonNull(product, "product cannot be NULL");
        return productRepository.save(product);
    }

    @Override
    public Product findByName(String name) throws ProductNotFoundException {
        Objects.requireNonNull(name, "name cannot be NULL");
        return productRepository.findProductByName(name).orElseThrow(() -> new ProductNotFoundException(String.format("Product with name %s cannot be found", name)));
    }

    @Override
    public void deleteProduct(Integer id) {
        Objects.requireNonNull(id, "Id cannot be NULL");
        productRepository.deleteById(id);
    }

}
