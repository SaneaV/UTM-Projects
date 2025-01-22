package com.example.demo.controller;

import com.example.demo.model.Brand;
import com.example.demo.repository.BrandRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

  @Autowired
  private BrandRepository brandRepository;

  @GetMapping
  public List<Brand> getAllBrands() {
    return brandRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Brand> getBrandById(@PathVariable Byte id) {
    Optional<Brand> brand = brandRepository.findById(id);
    return brand.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Brand createBrand(@RequestBody Brand brand) {
    return brandRepository.save(brand);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Brand> updateBrand(@PathVariable Byte id, @RequestBody Brand brandDetails) {
    Optional<Brand> brand = brandRepository.findById(id);
    if (brand.isPresent()) {
      Brand updatedBrand = brand.get();
      updatedBrand.setBrand(brandDetails.getBrand());
      return ResponseEntity.ok(brandRepository.save(updatedBrand));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBrand(@PathVariable Byte id) {
    if (brandRepository.existsById(id)) {
      brandRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}

