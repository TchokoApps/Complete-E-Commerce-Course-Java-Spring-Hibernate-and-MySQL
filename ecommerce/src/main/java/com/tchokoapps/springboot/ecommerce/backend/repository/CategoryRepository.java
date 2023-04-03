package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.name = :name")
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findAllByParentIsNull();

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL ORDER BY c.name ASC")
    List<Category> findAllByParentIsNullOrderByNameAsc();

    @Query("SELECT c FROM Category c WHERE c.parent.id = :id")
    List<Category> findAllByParentId(Integer id);

    @Query("SELECT c FROM Category c WHERE c.parent.id = :id ORDER BY c.name ASC")
    List<Category> findAllByParentIdOrderByNameAsc(Integer id);

}
