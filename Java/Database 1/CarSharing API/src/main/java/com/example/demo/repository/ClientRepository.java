package com.example.demo.repository;

import com.example.demo.model.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

  Optional<Client> findByPassportId(String passportId);

  Optional<Client> findByPhoneNumber(String phoneNumber);

  List<Client> findByLastName(String lastName);
}