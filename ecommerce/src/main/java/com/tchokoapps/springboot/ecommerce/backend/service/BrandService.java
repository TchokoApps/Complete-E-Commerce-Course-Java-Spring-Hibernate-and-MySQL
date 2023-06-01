package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.exception.BrandNotFoundExcepion;

import java.util.List;

public interface BrandService {

    List<Brand> findAll();

    List<Brand> findAllByOrderByCreatedTimeDesc();

    List<Brand> findAllByOrderByName();

    void save(Brand brand);

    Brand findById(Integer id) throws BrandNotFoundExcepion;

    void delete(Integer id) throws BrandNotFoundExcepion;

    Brand findByName(String name) throws BrandNotFoundExcepion;
}
