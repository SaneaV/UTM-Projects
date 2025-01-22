package com.example.demo.controller;

import com.example.demo.model.FuelType;
import com.example.demo.repository.FuelTypeRepository;
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
@RequestMapping("/api/fuel-types")
public class FuelTypeController {
  @Autowired
  private FuelTypeRepository fuelTypeRepository;

  @GetMapping
  public List<FuelType> getAllFuelTypes() {
    return fuelTypeRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<FuelType> getFuelTypeById(@PathVariable Byte id) {
    Optional<FuelType> fuelType = fuelTypeRepository.findById(id);
    return fuelType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public FuelType createFuelType(@RequestBody FuelType fuelType) {
    return fuelTypeRepository.save(fuelType);
  }

  @PutMapping("/{id}")
  public ResponseEntity<FuelType> updateFuelType(@PathVariable Byte id, @RequestBody FuelType fuelTypeDetails) {
    Optional<FuelType> fuelType = fuelTypeRepository.findById(id);
    if (fuelType.isPresent()) {
      FuelType updatedFuelType = fuelType.get();
      updatedFuelType.setFuel(fuelTypeDetails.getFuel());
      return ResponseEntity.ok(fuelTypeRepository.save(updatedFuelType));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFuelType(@PathVariable Byte id) {
    if (fuelTypeRepository.existsById(id)) {
      fuelTypeRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
