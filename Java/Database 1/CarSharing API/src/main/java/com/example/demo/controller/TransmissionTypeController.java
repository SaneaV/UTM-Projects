package com.example.demo.controller;

import com.example.demo.model.TransmissionType;
import com.example.demo.repository.TransmissionTypeRepository;
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
@RequestMapping("/api/transmission-types")
public class TransmissionTypeController {

  @Autowired
  private TransmissionTypeRepository transmissionTypeRepository;

  @GetMapping
  public List<TransmissionType> getAllTransmissionTypes() {
    return transmissionTypeRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransmissionType> getTransmissionTypeById(@PathVariable Byte id) {
    Optional<TransmissionType> transmissionType = transmissionTypeRepository.findById(id);
    return transmissionType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public TransmissionType createTransmissionType(@RequestBody TransmissionType transmissionType) {
    return transmissionTypeRepository.save(transmissionType);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TransmissionType> updateTransmissionType(@PathVariable Byte id,
      @RequestBody TransmissionType transmissionTypeDetails) {
    Optional<TransmissionType> transmissionType = transmissionTypeRepository.findById(id);
    if (transmissionType.isPresent()) {
      TransmissionType updatedTransmissionType = transmissionType.get();
      updatedTransmissionType.setTransmission(transmissionTypeDetails.getTransmission());
      return ResponseEntity.ok(transmissionTypeRepository.save(updatedTransmissionType));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTransmissionType(@PathVariable Byte id) {
    if (transmissionTypeRepository.existsById(id)) {
      transmissionTypeRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}