package com.example.demo.controller;

import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {

  @Autowired
  private CarRepository carRepository;

  @GetMapping
  public List<Car> getAllCars() {
    return carRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Car> getCarById(@PathVariable Integer id) {
    Optional<Car> car = carRepository.findById(id);
    return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Car createCar(@RequestBody Car car) {
    return carRepository.save(car);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Car> updateCar(@PathVariable Integer id, @RequestBody Car carDetails) {
    Optional<Car> car = carRepository.findById(id);
    if (car.isPresent()) {
      Car updatedCar = car.get();
      updatedCar.setLicensePlate(carDetails.getLicensePlate());
      updatedCar.setBrand(carDetails.getBrand());
      updatedCar.setCarModel(carDetails.getCarModel());
      updatedCar.setRentPrice(carDetails.getRentPrice());
      updatedCar.setStatus(carDetails.getStatus());
      return ResponseEntity.ok(carRepository.save(updatedCar));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
    if (carRepository.existsById(id)) {
      carRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/search")
  public ResponseEntity<List<Car>> getCarsByBrandAndModel(@RequestParam Byte brandId, @RequestParam String carModel) {
    List<Car> cars = carRepository.findCarsByBrandAndModel(brandId, carModel);
    return ResponseEntity.ok(cars);
  }
}
