package com.tchokoapps.springboot.ecommerce.common.bootstrap;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import com.tchokoapps.springboot.ecommerce.backend.repository.BrandRepository;
import com.tchokoapps.springboot.ecommerce.backend.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Slf4j
@AllArgsConstructor
@Component
public class CategoryCreator {

    private CategoryRepository categoryRepository;
    private BrandRepository brandRepository;

    public void createCategories() {
        Category category1 = Category.builder().name("Phones & Tablets").build();
        Category category1_1 = Category.builder().name("Smartphones").parent(category1).build();
        Category category1_2 = Category.builder().name("Tablets").parent(category1).build();
        Category category1_3 = Category.builder().name("Phone Accessories").parent(category1).build();
        Category category1_4 = Category.builder().name("Tablet Accessories").parent(category1).build();
        Category category1_5 = Category.builder().name("Landline Phones").parent(category1).build();
        Category category1_6 = Category.builder().name("SIM Cards & Credit").parent(category1).build();

        Category category2 = Category.builder().name("Computing").build();
        Category category2_1 = Category.builder().name("Laptops").parent(category2).build();
        Category category2_2 = Category.builder().name("Desktops & Monitors").parent(category2).build();
        Category category2_3 = Category.builder().name("Computer Accessories").parent(category2).build();
        Category category2_4 = Category.builder().name("Printers, Scanners & Fax").parent(category2).build();

        Category category3 = Category.builder().name("Electronics").build();
        Category category3_1 = Category.builder().name("Televisions").parent(category3).build();
        Category category3_2 = Category.builder().name("Home Audio & Speakers").parent(category3).build();
        Category category3_3 = Category.builder().name("DVD Players & Recorders").parent(category3).build();
        Category category3_4 = Category.builder().name("Cameras & Camcorders").parent(category3).build();
        Category category3_5 = Category.builder().name("Gaming Consoles").parent(category3).build();

        Category category4 = Category.builder().name("Home & Office").build();
        Category category4_1 = Category.builder().name("Home & Kitchen Appliances").parent(category4).build();
        Category category4_2 = Category.builder().name("Bedding & Bath").parent(category4).build();
        Category category4_3 = Category.builder().name("Furniture & Decor").parent(category4).build();
        Category category4_4 = Category.builder().name("Office Supplies & Stationery").parent(category4).build();
        Category category4_5 = Category.builder().name("Lighting & Fixtures").parent(category4).build();
        Category category4_6 = Category.builder().name("Home Improvement & Tools").parent(category4).build();

        Category category5 = Category.builder().name("Health & Beauty").build();
        Category category5_1 = Category.builder().name("Fragrances").parent(category5).build();
        Category category5_2 = Category.builder().name("Makeup").parent(category5).build();
        Category category5_3 = Category.builder().name("Personal Care").parent(category5).build();
        Category category5_4 = Category.builder().name("Hair Care & Styling").parent(category5).build();
        Category category5_5 = Category.builder().name("Sexual Wellness").parent(category5).build();

        Category category6 = Category.builder().name("Fashion").build();
        Category category6_1 = Category.builder().name("Women's Fashion").parent(category6).build();
        Category category6_2 = Category.builder().name("Men's Fashion").parent(category6).build();
        Category category6_3 = Category.builder().name("Kids Fashion").parent(category6).build();
        Category category6_4 = Category.builder().name("Watches").parent(category6).build();
        Category category6_5 = Category.builder().name("Jewelry").parent(category6).build();
        Category category6_6 = Category.builder().name("Bags & Accessories").parent(category6).build();

        Category category7 = Category.builder().name("Supermarket").build();
        Category category7_1 = Category.builder().name("Food Cupboard").parent(category7).build();
        Category category7_2 = Category.builder().name("Beverages").parent(category7).build();
        Category category7_3 = Category.builder().name("Cleaning & Household").parent(category7).build();
        Category category7_4 = Category.builder().name("Beauty & Personal Care").parent(category7).build();
        Category category7_5 = Category.builder().name("All Baby Products").parent(category7).build();
        Category category7_6 = Category.builder().name("Pet Supplies").parent(category7).build();

        Category category8 = Category.builder().name("Sports & Fitness").build();
        Category category8_1 = Category.builder().name("Exercise & Fitness").parent(category8).build();
        Category category8_2 = Category.builder().name("Outdoor Recreation").parent(category8).build();
        Category category8_3 = Category.builder().name("Sports Apparel").parent(category8).build();
        Category category8_4 = Category.builder().name("Sports Shoes").parent(category8).build();
        Category category8_5 = Category.builder().name("Sports Accessories").parent(category8).build();

        Category category9 = Category.builder().name("Baby Products").build();
        Category category9_1 = Category.builder().name("Baby Gear").parent(category9).build();
        Category category9_2 = Category.builder().name("Diapers & Potty Training").parent(category9).build();
        Category category9_3 = Category.builder().name("Feeding").parent(category9).build();
        Category category9_4 = Category.builder().name("Baby & Toddler Toys").parent(category9).build();
        Category category9_5 = Category.builder().name("Baby Fashion").parent(category9).build();

        categoryRepository.saveAll(Arrays.asList(category1, category1_1, category1_2, category1_3, category1_4, category1_5, category1_6, category2, category2_1, category2_2, category2_3, category2_4, category3, category3_1, category3_2, category3_3, category3_4, category3_5, category4, category4_1, category4_2, category4_3, category4_4, category4_5, category4_6, category5, category5_1, category5_2, category5_3, category5_4, category5_5, category6, category6_1, category6_2, category6_3, category6_4, category6_5, category6_6, category7, category7_1, category7_2, category7_3, category7_4, category7_5, category7_6, category8, category8_1, category8_2, category8_3, category8_4, category8_5, category9, category9_1, category9_2, category9_3, category9_4, category9_5));
    }

}
