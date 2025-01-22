package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;
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
@RequestMapping("/api/clients")
public class ClientController {

  @Autowired
  private ClientRepository clientRepository;

  @GetMapping
  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
    Optional<Client> client = clientRepository.findById(id);
    return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public Client createClient(@RequestBody Client client) {
    return clientRepository.save(client);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody Client clientDetails) {
    Optional<Client> client = clientRepository.findById(id);
    if (client.isPresent()) {
      Client updatedClient = client.get();
      updatedClient.setFirstName(clientDetails.getFirstName());
      updatedClient.setLastName(clientDetails.getLastName());
      updatedClient.setPassportId(clientDetails.getPassportId());
      updatedClient.setPhoneNumber(clientDetails.getPhoneNumber());
      updatedClient.setClientAddress(clientDetails.getClientAddress());
      updatedClient.setBirthDate(clientDetails.getBirthDate());
      return ResponseEntity.ok(clientRepository.save(updatedClient));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
    if (clientRepository.existsById(id)) {
      clientRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}