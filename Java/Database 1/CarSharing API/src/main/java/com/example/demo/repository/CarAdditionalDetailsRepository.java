package com.example.demo.repository;

import com.example.demo.model.BodyType;
import com.example.demo.model.CarAdditionalDetails;
import com.example.demo.model.Color;
import com.example.demo.model.FuelType;
import com.example.demo.model.TransmissionType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarAdditionalDetailsRepository extends JpaRepository<CarAdditionalDetails, Integer> {

  List<CarAdditionalDetails> findByColor(Color color);

  List<CarAdditionalDetails> findByFuel(FuelType fuel);

  List<CarAdditionalDetails> findByTransmission(TransmissionType transmission);

  List<CarAdditionalDetails> findByBodyType(BodyType bodyType);
}