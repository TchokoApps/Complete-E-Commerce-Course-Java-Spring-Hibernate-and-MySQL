package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testCrateNewProduct() {

        Category category = Category.builder()
                .name("Electronics")
                .build();

        Brand brand = Brand.builder()
                .name("Apple")
                .build();

        Product product = Product.builder()
                .name("iPhone 13")
                .alias("iphone13")
                .shortDescription("The latest and greatest iPhone")
                .fullDescription("The iPhone 13 is Apple's newest smartphone offering. It features a stunning 6.1-inch OLED display, 5G connectivity, and an A15 Bionic chip.")
                .inStock(true)
                .cost(BigDecimal.valueOf(700))
                .price(BigDecimal.valueOf(999))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(5.78))
                .width(BigDecimal.valueOf(2.82))
                .weight(BigDecimal.valueOf(2.82))
                .height(BigDecimal.valueOf(0.3))
                .weight(BigDecimal.valueOf(0.29))
                .category(category)
                .brand(brand)
                .build();

        Product product2 = Product.builder()
                .name("Samsung Galaxy S21 Ultra")
                .alias("galaxys21ultra")
                .shortDescription("The ultimate Android phone")
                .fullDescription("The Samsung Galaxy S21 Ultra is a top-of-the-line Android smartphone with a huge 6.8-inch Dynamic AMOLED display, 5G connectivity, and an Exynos 2100 processor")
                .inStock(true)
                .cost(BigDecimal.valueOf(800))
                .price(BigDecimal.valueOf(1199))
                .discountPercent(BigDecimal.valueOf(0))
                .length(BigDecimal.valueOf(6.5))
                .width(BigDecimal.valueOf(3))
                .weight(BigDecimal.valueOf(2.82))
                .height(BigDecimal.valueOf(0.4))
                .weight(BigDecimal.valueOf(0.33))
                .category(category)
                .brand(brand)
                .build();

        Category categorySaved = categoryRepository.save(category);
        Brand brandSaved = brandRepository.save(brand);

        assertThat(categorySaved.getId()).isNotNull();
        assertThat(brandSaved.getId()).isNotNull();

        List<Product> products = productRepository.saveAll(Arrays.asList(product, product2));

        assertThat(products.size()).isEqualTo(2);

        products.forEach(item -> assertThat(item.getId()).isNotNull());
    }

}