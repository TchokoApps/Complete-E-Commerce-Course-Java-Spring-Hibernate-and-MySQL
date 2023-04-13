package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.entity.Product;
import com.tchokoapps.springboot.ecommerce.backend.entity.ProductDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductDetailRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testProductDetails() {

        Category category = Category.builder()
                .name("Electronics")
                .build();

        categoryRepository.save(category);

        Brand brand = Brand.builder()
                .name("Apple")
                .build();

        brandRepository.save(brand);

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
                .categories(Collections.singletonList(category))
                .brand(brand)
                .build();

        productRepository.save(product);

        ProductDetail productDetail1 = ProductDetail.builder()
                .name("Processor")
                .productDetailValue("Intel Celeron Processor N4020 (4M Cache, 1.10 GHz up to 2.80 GHz)")
                .product(product)
                .build();

        ProductDetail productDetail2 = ProductDetail.builder()
                .name("Display")
                .productDetailValue("14\" FHD (1920 x 1080) IPS Display")
                .product(product)
                .build();

        ProductDetail productDetail3 = ProductDetail.builder()
                .name("Memory")
                .productDetailValue("4GB LPDDR4 RAM")
                .product(product)
                .build();

        ProductDetail productDetail4 = ProductDetail.builder()
                .name("Storage")
                .productDetailValue("256GB SATA M.2 SSD")
                .product(product)
                .build();

        List<ProductDetail> productDetails =
                productDetailRepository.saveAll(Arrays.asList(productDetail1, productDetail2, productDetail3, productDetail4));

        System.out.println(productDetails);

        productDetails.forEach(productDetail -> assertThat(productDetail.getId()).isNotNull());

    }

}