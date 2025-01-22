package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cars_additional_details")
public class CarAdditionalDetails {

  @Id
  private Integer id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "id")
  private Car car;

  @ManyToOne
  @JoinColumn(name = "colorId", nullable = false)
  private Color color;

  @ManyToOne
  @JoinColumn(name = "fuelId", nullable = false)
  private FuelType fuel;

  @ManyToOne
  @JoinColumn(name = "transmissionId", nullable = false)
  private TransmissionType transmission;

  @ManyToOne
  @JoinColumn(name = "bodyTypeId", nullable = false)
  private BodyType bodyType;

  @Column(nullable = false)
  private Byte seatCount;
}