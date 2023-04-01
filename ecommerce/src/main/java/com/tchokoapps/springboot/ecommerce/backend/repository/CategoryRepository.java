package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    List<Category> findAllByParentIsNull();

    List<Category> findAllByParentIsNullOrderByNameAsc();

    List<Category> findAllByParentId(Integer id);

    List<Category> findAllByParentIdOrderByNameAsc(Integer id);

}
