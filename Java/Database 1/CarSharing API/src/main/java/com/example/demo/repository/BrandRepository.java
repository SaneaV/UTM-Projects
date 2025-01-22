package com.example.demo.repository;

import com.example.demo.model.Brand;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Byte> {

  Optional<Brand> findByBrand(String brand);
}
