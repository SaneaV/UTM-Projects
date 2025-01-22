package com.example.demo.controller;

import com.example.demo.model.Feature;
import com.example.demo.repository.FeatureRepository;
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
@RequestMapping("/api/features")
public class FeatureController {

  @Autowired
  private FeatureRepository featureRepository;

  @GetMapping
  public List<Feature> getAllFeatures() {
    return featureRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Feature> getFeatureById(@PathVariable Integer id) {
    Optional<Feature> feature = featureRepository.findById(id);
    return feature.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Feature createFeature(@RequestBody Feature feature) {
    return featureRepository.save(feature);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Feature> updateFeature(@PathVariable Integer id, @RequestBody Feature featureDetails) {
    Optional<Feature> feature = featureRepository.findById(id);
    if (feature.isPresent()) {
      Feature updatedFeature = feature.get();
      updatedFeature.setFeature(featureDetails.getFeature());
      return ResponseEntity.ok(featureRepository.save(updatedFeature));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFeature(@PathVariable Integer id) {
    if (featureRepository.existsById(id)) {
      featureRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}