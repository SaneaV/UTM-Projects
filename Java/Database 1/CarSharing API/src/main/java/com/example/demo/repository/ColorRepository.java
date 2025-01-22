package com.example.demo.repository;

import com.example.demo.model.Color;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Byte> {

  Optional<Color> findByColor(String color);
}