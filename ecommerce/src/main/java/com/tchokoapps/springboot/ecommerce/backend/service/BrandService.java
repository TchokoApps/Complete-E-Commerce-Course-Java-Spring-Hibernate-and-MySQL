package com.tchokoapps.springboot.ecommerce.backend.service;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.exception.BrandNotFoundExcepion;
import com.tchokoapps.springboot.ecommerce.backend.repository.BrandRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class BrandService {

    private BrandRepository brandRepository;

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public void save(Brand brand) {

        Objects.requireNonNull(brand, "Brand cannot be NULL");
        log.info("save - saving brand {}", brand);
        Brand brandSaved = brandRepository.save(brand);
        log.info("save - Brand {} saved successfully", brandSaved);

    }

    public Brand findById(Integer id) throws BrandNotFoundExcepion {

        Objects.requireNonNull(id, "Id cannot be NULL");
        log.info("findById - finding by id {}", id);
        Brand brandFound = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundExcepion(String.format("Could not find any Brand with ID %s", id)));
        log.info("findById - Brand found {}", brandFound);
        return brandFound;

    }

    public void delete(Integer id) throws BrandNotFoundExcepion {

        Objects.requireNonNull(id, "Id cannot be NULL");
        log.info("delete - deleting brand(id={})", id);
        Brand brandFound = brandRepository.findById(id).orElseThrow(() -> new BrandNotFoundExcepion(String.format("Could not find any Brand with ID %s", id)));
        brandRepository.delete(brandFound);
        log.info("delete - brand(id={}) deleted successfully", id);

    }

    public Brand findByName(String name) throws BrandNotFoundExcepion {

        Objects.requireNonNull(name, "name cannot be NULL");
        log.info("findByName - Finding brand(name={})", name);
        Brand brandFound = brandRepository.findByName(name).orElseThrow(() -> new BrandNotFoundExcepion(String.format("Could not find any Brand with name %s", name)));
        log.info("findByName - Brand(name={}) found successfully", name);
        return brandFound;

    }


}
