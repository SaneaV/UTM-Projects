package com.example.demo.repository;

import com.example.demo.model.Feature;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {

  Optional<Feature> findByFeature(String feature);
}