package com.example.demo.request;

import com.example.demo.model.BodyType;
import com.example.demo.model.Brand;
import com.example.demo.model.Color;
import com.example.demo.model.FuelType;
import com.example.demo.model.TransmissionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CarReferenceRequest {
  private final Brand brand;
  private final Color color;
  private final FuelType fuelType;
  private final TransmissionType transmissionType;
  private final BodyType bodyType;
}
