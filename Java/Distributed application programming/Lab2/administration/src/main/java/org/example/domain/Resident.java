package org.example.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "residents")
public class Resident {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "birth_date")
  private LocalDate birthDate;
  @Column(name = "phone_number")
  private String phoneNumber;
  @Column(name = "passport_number")
  private String passportNumber;
}