package com.example.demo.controller;

import com.example.demo.request.CarReferenceRequest;
import com.example.demo.service.CarReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/car-references")
public class CarReferenceController {

  @Autowired
  private CarReferenceService carReferenceService;

  @PostMapping("/add")
  public ResponseEntity<String> addNewCarReferences(@RequestBody CarReferenceRequest request) {
    try {
      carReferenceService.addNewCarReferences(
          request.getBrand(),
          request.getColor(),
          request.getFuelType(),
          request.getTransmissionType(),
          request.getBodyType()
      );
      return ResponseEntity.ok("Successfully added new elements");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Transaction rolled back");
    }
  }
}