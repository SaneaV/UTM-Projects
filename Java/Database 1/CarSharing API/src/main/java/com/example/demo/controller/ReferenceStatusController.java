package com.example.demo.controller;

import com.example.demo.model.ReferenceStatus;
import com.example.demo.repository.ReferenceStatusRepository;
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
@RequestMapping("/api/reference-statuses")
public class ReferenceStatusController {

  @Autowired
  private ReferenceStatusRepository referenceStatusRepository;

  @GetMapping
  public List<ReferenceStatus> getAllReferenceStatuses() {
    return referenceStatusRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReferenceStatus> getReferenceStatusById(@PathVariable Byte id) {
    Optional<ReferenceStatus> referenceStatus = referenceStatusRepository.findById(id);
    return referenceStatus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ReferenceStatus createReferenceStatus(@RequestBody ReferenceStatus referenceStatus) {
    return referenceStatusRepository.save(referenceStatus);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReferenceStatus> updateReferenceStatus(@PathVariable Byte id,
      @RequestBody ReferenceStatus referenceStatusDetails) {
    Optional<ReferenceStatus> referenceStatus = referenceStatusRepository.findById(id);
    if (referenceStatus.isPresent()) {
      ReferenceStatus updatedReferenceStatus = referenceStatus.get();
      updatedReferenceStatus.setStatus(referenceStatusDetails.getStatus());
      return ResponseEntity.ok(referenceStatusRepository.save(updatedReferenceStatus));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReferenceStatus(@PathVariable Byte id) {
    if (referenceStatusRepository.existsById(id)) {
      referenceStatusRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}