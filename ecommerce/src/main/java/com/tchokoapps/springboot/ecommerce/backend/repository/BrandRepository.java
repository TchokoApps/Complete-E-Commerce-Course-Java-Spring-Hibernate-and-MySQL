package com.tchokoapps.springboot.ecommerce.backend.repository;

import com.tchokoapps.springboot.ecommerce.backend.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("SELECT b FROM Brand b WHERE b.name = :name")
    Optional<Brand> findByName(String name);

    @Query("SELECT b FROM Brand b ORDER BY b.name ASC")
    List<Brand> findAllByOrderByNameAsc();

    @Query("SELECT b FROM Brand b ORDER BY b.name ASC")
    List<Brand> findAllByOrderByCreatedTimeDesc();

}
