package com.tchokoapps.springboot.ecommerce.common.bootstrap;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.repository.BrandRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@AllArgsConstructor
public class BrandCreator {

    private BrandRepository brandRepository;

    public void createBrands() {
        Brand brand = Brand.builder().name("Nike").build();
        Brand brand2 = Brand.builder().name("Adidas").build();
        Brand brand3 = Brand.builder().name("Fila").build();
        Brand brand4 = Brand.builder().name("Puma").build();
        Brand brand5 = Brand.builder().name("Reebok").build();
        Brand brand6 = Brand.builder().name("Converse").build();
        Brand brand7 = Brand.builder().name("Vans").build();
        Brand brand8 = Brand.builder().name("New Balance").build();
        Brand brand9 = Brand.builder().name("Under Armour").build();
        Brand brand10 = Brand.builder().name("ASICS").build();
        Brand brand11 = Brand.builder().name("Saucony").build();
        Brand brand12 = Brand.builder().name("Brooks").build();
        Brand brand13 = Brand.builder().name("Lululemon").build();
        Brand brand14 = Brand.builder().name("Gymshark").build();
        Brand brand15 = Brand.builder().name("Athleta").build();
        Brand brand16 = Brand.builder().name("Alo Yoga").build();
        Brand brand17 = Brand.builder().name("Lorna Jane").build();
        Brand brand18 = Brand.builder().name("Varley").build();
        Brand brand19 = Brand.builder().name("Outdoor Voices").build();
        Brand brand20 = Brand.builder().name("Carbon38").build();

        brandRepository.saveAll(Arrays.asList(brand, brand2, brand3, brand4, brand5, brand6, brand7, brand8, brand9, brand10, brand11, brand12, brand13, brand14, brand15, brand16, brand17, brand18, brand19, brand20));
    }

}
