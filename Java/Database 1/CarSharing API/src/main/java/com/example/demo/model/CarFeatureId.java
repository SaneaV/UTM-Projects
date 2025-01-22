package com.example.demo.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class CarFeatureId implements Serializable {

  private Integer carId;
  private Integer featureId;
}