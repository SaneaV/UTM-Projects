package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fuel_types")
public class FuelType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Byte id;

  @Column(nullable = false, unique = true)
  private String fuel;
}