package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client_car_contracts")
public class ClientCarContract {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long contractId;

  @ManyToOne
  @JoinColumn(name = "clientId", nullable = false)
  private Client client;

  @ManyToOne
  @JoinColumn(name = "carId", nullable = false)
  private Car car;

  @Column(nullable = false)
  private Date orderDate;

  @Column(nullable = false)
  private Date startDate;

  @Column(nullable = false)
  private Date endDate;

  @Transient
  private Long totalDays;

  @PostLoad
  @PostPersist
  @PostUpdate
  private void calculateTotalDays() {
    this.totalDays = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
  }
}