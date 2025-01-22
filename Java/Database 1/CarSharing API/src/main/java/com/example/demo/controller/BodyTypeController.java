package com.example.demo.controller;

import com.example.demo.model.BodyType;
import com.example.demo.repository.BodyTypeRepository;
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
@RequestMapping("/api/body-types")
public class BodyTypeController {

  @Autowired
  private BodyTypeRepository bodyTypeRepository;

  @GetMapping
  public List<BodyType> getAllBodyTypes() {
    return bodyTypeRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BodyType> getBodyTypeById(@PathVariable Byte id) {
    Optional<BodyType> bodyType = bodyTypeRepository.findById(id);
    return bodyType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public BodyType createBodyType(@RequestBody BodyType bodyType) {
    return bodyTypeRepository.save(bodyType);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BodyType> updateBodyType(@PathVariable Byte id, @RequestBody BodyType bodyTypeDetails) {
    Optional<BodyType> bodyType = bodyTypeRepository.findById(id);
    if (bodyType.isPresent()) {
      BodyType updatedBodyType = bodyType.get();
      updatedBodyType.setBodyType(bodyTypeDetails.getBodyType());
      return ResponseEntity.ok(bodyTypeRepository.save(updatedBodyType));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBodyType(@PathVariable Byte id) {
    if (bodyTypeRepository.existsById(id)) {
      bodyTypeRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
