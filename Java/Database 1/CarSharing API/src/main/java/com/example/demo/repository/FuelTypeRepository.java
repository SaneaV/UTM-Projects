package com.example.demo.repository;

import com.example.demo.model.FuelType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelTypeRepository extends JpaRepository<FuelType, Byte> {

  Optional<FuelType> findByFuel(String fuel);
}