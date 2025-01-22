package com.example.demo.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "car_features")
public class CarFeature {

  @EmbeddedId
  private CarFeatureId id;

  @ManyToOne
  @MapsId("carId")
  @JoinColumn(name = "carId")
  private Car car;

  @ManyToOne
  @MapsId("featureId")
  @JoinColumn(name = "featureId")
  private Feature feature;
}