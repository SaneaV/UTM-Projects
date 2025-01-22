package com.example.demo.repository;

import com.example.demo.model.TransmissionType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionTypeRepository extends JpaRepository<TransmissionType, Byte> {

  Optional<TransmissionType> findByTransmission(String transmission);
}
