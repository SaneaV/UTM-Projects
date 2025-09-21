package org.example;

import jakarta.persistence.*;
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

  private String firstName;
  private String lastName;
  private LocalDate birthDate;
  private String phoneNumber;
  private String passportNumber;
}