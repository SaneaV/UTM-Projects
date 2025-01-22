package com.example.demo.repository;

import com.example.demo.model.BodyType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyTypeRepository extends JpaRepository<BodyType, Byte> {

  Optional<BodyType> findByBodyType(String bodyType);
}
