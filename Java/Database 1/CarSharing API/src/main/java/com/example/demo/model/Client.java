package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String passportId;

  @Column(nullable = false, unique = true)
  private String phoneNumber;

  private String clientAddress;

  @Column(nullable = false)
  private Date birthDate;

  @OneToMany(mappedBy = "client")
  private Set<ClientCarContract> contracts;
}