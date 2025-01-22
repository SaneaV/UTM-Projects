package com.example.demo.controller;

import com.example.demo.model.CarAdditionalDetails;
import com.example.demo.repository.CarAdditionalDetailsRepository;
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
@RequestMapping("/api/car-additional-details")
public class CarAdditionalDetailsController {

  @Autowired
  private CarAdditionalDetailsRepository carAdditionalDetailsRepository;

  @GetMapping
  public List<CarAdditionalDetails> getAllCarAdditionalDetails() {
    return carAdditionalDetailsRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<CarAdditionalDetails> getCarAdditionalDetailsById(@PathVariable Integer id) {
    Optional<CarAdditionalDetails> carAdditionalDetails = carAdditionalDetailsRepository.findById(id);
    return carAdditionalDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public CarAdditionalDetails createCarAdditionalDetails(@RequestBody CarAdditionalDetails carAdditionalDetails) {
    return carAdditionalDetailsRepository.save(carAdditionalDetails);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CarAdditionalDetails> updateCarAdditionalDetails(@PathVariable Integer id,
      @RequestBody CarAdditionalDetails carAdditionalDetailsDetails) {
    Optional<CarAdditionalDetails> carAdditionalDetails = carAdditionalDetailsRepository.findById(id);
    if (carAdditionalDetails.isPresent()) {
      CarAdditionalDetails updatedCarAdditionalDetails = carAdditionalDetails.get();
      updatedCarAdditionalDetails.setColor(carAdditionalDetailsDetails.getColor());
      updatedCarAdditionalDetails.setFuel(carAdditionalDetailsDetails.getFuel());
      updatedCarAdditionalDetails.setTransmission(carAdditionalDetailsDetails.getTransmission());
      updatedCarAdditionalDetails.setBodyType(carAdditionalDetailsDetails.getBodyType());
      updatedCarAdditionalDetails.setSeatCount(carAdditionalDetailsDetails.getSeatCount());
      return ResponseEntity.ok(carAdditionalDetailsRepository.save(updatedCarAdditionalDetails));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCarAdditionalDetails(@PathVariable Integer id) {
    if (carAdditionalDetailsRepository.existsById(id)) {
      carAdditionalDetailsRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}