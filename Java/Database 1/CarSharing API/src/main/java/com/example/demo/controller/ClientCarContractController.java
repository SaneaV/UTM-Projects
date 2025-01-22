package com.example.demo.controller;

import com.example.demo.model.ClientCarContract;
import com.example.demo.repository.ClientCarContractRepository;
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
@RequestMapping("/api/contracts")
public class ClientCarContractController {

  @Autowired
  private ClientCarContractRepository contractRepository;

  @GetMapping
  public List<ClientCarContract> getAllContracts() {
    return contractRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientCarContract> getContractById(@PathVariable Long id) {
    Optional<ClientCarContract> contract = contractRepository.findById(id);
    return contract.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ClientCarContract createContract(@RequestBody ClientCarContract contract) {
    return contractRepository.save(contract);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientCarContract> updateContract(@PathVariable Long id, @RequestBody ClientCarContract contractDetails) {
    Optional<ClientCarContract> contract = contractRepository.findById(id);
    if (contract.isPresent()) {
      ClientCarContract updatedContract = contract.get();
      updatedContract.setClient(contractDetails.getClient());
      updatedContract.setCar(contractDetails.getCar());
      updatedContract.setOrderDate(contractDetails.getOrderDate());
      updatedContract.setStartDate(contractDetails.getStartDate());
      updatedContract.setEndDate(contractDetails.getEndDate());
      return ResponseEntity.ok(contractRepository.save(updatedContract));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
    if (contractRepository.existsById(id)) {
      contractRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
