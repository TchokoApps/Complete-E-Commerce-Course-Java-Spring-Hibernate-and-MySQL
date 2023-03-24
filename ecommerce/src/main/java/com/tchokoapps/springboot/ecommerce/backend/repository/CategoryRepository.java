package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
