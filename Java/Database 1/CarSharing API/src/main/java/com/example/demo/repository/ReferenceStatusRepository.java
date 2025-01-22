package com.example.demo.repository;

import com.example.demo.model.ReferenceStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceStatusRepository extends JpaRepository<ReferenceStatus, Byte> {

  Optional<ReferenceStatus> findByStatus(String status);
}