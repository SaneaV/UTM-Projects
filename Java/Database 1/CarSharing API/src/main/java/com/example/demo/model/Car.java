package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String licensePlate;

  @ManyToOne
  @JoinColumn(name = "brandId", nullable = false)
  private Brand brand;

  @Column(nullable = false)
  private String carModel;

  @Column(nullable = false)
  private Double rentPrice;

  @ManyToOne
  @JoinColumn(name = "statusId", nullable = false)
  private ReferenceStatus status;

  @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
  private CarAdditionalDetails additionalDetails;

  @OneToMany(mappedBy = "car")
  private Set<CarFeature> carFeatures;
}