package com.tchokoapps.springboot.ecommerce.common.bootstrap;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.entity.Product;
import com.tchokoapps.springboot.ecommerce.backend.exception.BrandNotFoundExcepion;
import com.tchokoapps.springboot.ecommerce.backend.exception.CategoryNotFoundException;
import com.tchokoapps.springboot.ecommerce.backend.repository.BrandRepository;
import com.tchokoapps.springboot.ecommerce.backend.repository.CategoryRepository;
import com.tchokoapps.springboot.ecommerce.backend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class ProductCreator {

    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public void createProducts() throws BrandNotFoundExcepion, CategoryNotFoundException {
        int brandId = 1;
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new BrandNotFoundExcepion(String.format("Brand with id %s cannot be found", brandId)));

        int categoryId = 1;
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id %s cannot be found", categoryId)));

        Product product1 = Product.builder()
                .name("Samsung Galaxy S22")
                .alias("galaxyS22")
                .shortDescription("The latest and greatest Samsung phone")
                .fullDescription("The Samsung Galaxy S22 is the newest offering from Samsung. It features a stunning 6.8-inch AMOLED display, 5G connectivity, and an Exynos 2200 chip.")
                .cost(BigDecimal.valueOf(750))
                .price(BigDecimal.valueOf(1199))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(6.51))
                .width(BigDecimal.valueOf(3.02))
                .height(BigDecimal.valueOf(0.33))
                .weight(BigDecimal.valueOf(0.25))
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        Product product2 = Product.builder()
                .name("Dell XPS 15")
                .alias("xps15")
                .shortDescription("A high-performance laptop")
                .fullDescription("The Dell XPS 15 is a high-performance laptop featuring a 15-inch 4K display, Intel Core i7 processor, and Nvidia GeForce GTX 1650 graphics card.")
                .cost(BigDecimal.valueOf(1200))
                .price(BigDecimal.valueOf(1699))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(14.06))
                .width(BigDecimal.valueOf(9.7))
                .height(BigDecimal.valueOf(0.71))
                .weight(BigDecimal.valueOf(4.5))
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        Product product3 = Product.builder()
                .name("Canon EOS R5")
                .alias("eosR5")
                .shortDescription("A high-end mirrorless camera")
                .fullDescription("The Canon EOS R5 is a high-end mirrorless camera featuring a 45-megapixel sensor, 8K video recording, and in-body image stabilization.")
                .cost(BigDecimal.valueOf(3000))
                .price(BigDecimal.valueOf(3899))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(5.45))
                .width(BigDecimal.valueOf(3.84))
                .height(BigDecimal.valueOf(3.46))
                .weight(BigDecimal.valueOf(1.63))
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        Product product4 = Product.builder()
                .name("Beats Studio Buds")
                .alias("studioBuds")
                .shortDescription("Wireless earbuds with active noise cancellation")
                .fullDescription("The Beats Studio Buds are wireless earbuds with active noise cancellation and up to 8 hours of battery life.")
                .cost(BigDecimal.valueOf(100))
                .price(BigDecimal.valueOf(149))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(5.5))
                .width(BigDecimal.valueOf(5.5))
                .height(BigDecimal.valueOf(1.5))
                .weight(BigDecimal.valueOf(0.05))
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        Product product6 = Product.builder()
                .name("Samsung Galaxy S22 Ultra")
                .alias("s22u")
                .shortDescription("The ultimate flagship phone")
                .fullDescription("The Samsung Galaxy S22 Ultra is the latest flagship phone from Samsung. It features a 6.8-inch Dynamic AMOLED display, an upgraded camera system with 10x hybrid zoom, and a powerful Exynos 2200 processor. It also offers 5G connectivity and a long-lasting battery life.")
                .cost(BigDecimal.valueOf(800))
                .price(BigDecimal.valueOf(1199))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(6.53))
                .width(BigDecimal.valueOf(3.07))
                .height(BigDecimal.valueOf(0.35))
                .weight(BigDecimal.valueOf(0.23))
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        Product product5 = Product.builder()
                .name("Bose QuietComfort 35 II")
                .alias("quietcomfort35ii")
                .shortDescription("The ultimate noise-cancelling headphones")
                .fullDescription("The Bose QuietComfort 35 II headphones are wireless noise-cancelling headphones with up to 20 hours of battery life and built-in Alexa and Google Assistant.")
                .cost(BigDecimal.valueOf(250))
                .price(BigDecimal.valueOf(349))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(8.2))
                .width(BigDecimal.valueOf(6.7))
                .height(BigDecimal.valueOf(3.2))
                .weight(BigDecimal.valueOf(0.24))
                .categories(Collections.singletonList(category))
                .brand(brand)

                .build();

        Product product7 = Product.builder()
                .name("Sony WH-1000XM4")
                .alias("wh1000xm4")
                .shortDescription("Wireless noise cancelling headphones")
                .fullDescription("The Sony WH-1000XM4 is a high-quality pair of wireless headphones with noise-cancelling technology, up to 30 hours of battery life, and excellent sound quality.")
                .cost(BigDecimal.valueOf(300))
                .price(BigDecimal.valueOf(349))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(9.94))
                .width(BigDecimal.valueOf(7.67))
                .height(BigDecimal.valueOf(3.03))
                .weight(BigDecimal.valueOf(0.94))
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        Product product8 = Product.builder()
                .name("Nintendo Switch")
                .alias("switch")
                .shortDescription("A versatile gaming console")
                .fullDescription("The Nintendo Switch is a unique gaming console that can be played on a TV or taken on-the-go. It features a 6.2-inch touch screen, up to 9 hours of battery life, and a variety of games.")
                .cost(BigDecimal.valueOf(250))
                .price(BigDecimal.valueOf(299))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(9.41))
                .width(BigDecimal.valueOf(4.02))
                .height(BigDecimal.valueOf(0.55))
                .weight(BigDecimal.valueOf(0.65))
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        Product product9 = Product.builder()
                .name("Fitbit Charge 5")
                .alias("charge5")
                .shortDescription("A premium fitness tracker")
                .fullDescription("The Fitbit Charge 5 is a high-end fitness tracker with a heart rate monitor, built-in GPS, and up to 7 days of battery life. It also tracks your sleep and offers personalized insights.")
                .cost(BigDecimal.valueOf(150))
                .price(BigDecimal.valueOf(179))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(8.9))
                .width(BigDecimal.valueOf(2.5))
                .height(BigDecimal.valueOf(1.2))
                .weight(BigDecimal.valueOf(0.03))
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        Product product10 = Product.builder()
                .name("Apple Watch Series 7")
                .alias("watch7")
                .shortDescription("The ultimate smartwatch")
                .fullDescription("The Apple Watch Series 7 is the latest iteration of Apple's smartwatch. It features a larger, always-on Retina display, improved durability, and up to 18 hours of battery life. It also offers advanced health monitoring and fitness tracking.")
                .cost(BigDecimal.valueOf(400))
                .price(BigDecimal.valueOf(499))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(4.28))
                .width(BigDecimal.valueOf(3.43))
                .height(BigDecimal.valueOf(0.99))
                .weight(BigDecimal.valueOf(0.05))
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10);

        productRepository.saveAll(products);
    }
}
