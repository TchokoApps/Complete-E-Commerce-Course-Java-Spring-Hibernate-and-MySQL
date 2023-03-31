package com.tchokoapps.springboot.ecommerce.common.bootstrap;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ObjectCreator implements CommandLineRunner {

    private BrandCreator brandCreator;
    private CategoryCreator categoryCreator;
    private UserCreator userCreator;
    private ProductCreator productCreator;

    @Override
    public void run(String... args) throws Exception {
        brandCreator.createBrands();
        categoryCreator.createCategories();
        userCreator.createUsers();
        productCreator.createProducts();
    }
}
