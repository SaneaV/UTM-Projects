package com.example.demo.controller;

import com.example.demo.model.CarFeature;
import com.example.demo.model.CarFeatureId;
import com.example.demo.repository.CarFeatureRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/car-features")
public class CarFeatureController {

  @Autowired
  private CarFeatureRepository carFeatureRepository;

  @GetMapping
  public List<CarFeature> getAllCarFeatures() {
    return carFeatureRepository.findAll();
  }

  @GetMapping("/{carId}/{featureId}")
  public ResponseEntity<CarFeature> getCarFeatureById(@PathVariable Integer carId, @PathVariable Integer featureId) {
    CarFeatureId id = new CarFeatureId();
    id.setCarId(carId);
    id.setFeatureId(featureId);
    Optional<CarFeature> carFeature = carFeatureRepository.findById(id);
    return carFeature.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public CarFeature createCarFeature(@RequestBody CarFeature carFeature) {
    return carFeatureRepository.save(carFeature);
  }

  @PutMapping("/{carId}/{featureId}")
  public ResponseEntity<CarFeature> updateCarFeature(@PathVariable Integer carId, @PathVariable Integer featureId,
      @RequestBody CarFeature carFeatureDetails) {
    CarFeatureId id = new CarFeatureId();
    id.setCarId(carId);
    id.setFeatureId(featureId);
    Optional<CarFeature> carFeature = carFeatureRepository.findById(id);
    if (carFeature.isPresent()) {
      CarFeature updatedCarFeature = carFeature.get();
      updatedCarFeature.setCar(carFeatureDetails.getCar());
      updatedCarFeature.setFeature(carFeatureDetails.getFeature());
      return ResponseEntity.ok(carFeatureRepository.save(updatedCarFeature));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{carId}/{featureId}")
  public ResponseEntity<Void> deleteCarFeature(@PathVariable Integer carId, @PathVariable Integer featureId) {
    CarFeatureId id = new CarFeatureId();
    id.setCarId(carId);
    id.setFeatureId(featureId);
    if (carFeatureRepository.existsById(id)) {
      carFeatureRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}