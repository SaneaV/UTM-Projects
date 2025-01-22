package com.example.demo.service;

import com.example.demo.model.BodyType;
import com.example.demo.model.Brand;
import com.example.demo.model.Color;
import com.example.demo.model.FuelType;
import com.example.demo.model.TransmissionType;
import com.example.demo.repository.BodyTypeRepository;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.ColorRepository;
import com.example.demo.repository.FuelTypeRepository;
import com.example.demo.repository.TransmissionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarReferenceService {

  @Autowired
  private BrandRepository brandRepository;

  @Autowired
  private ColorRepository colorRepository;

  @Autowired
  private FuelTypeRepository fuelTypeRepository;

  @Autowired
  private TransmissionTypeRepository transmissionTypeRepository;

  @Autowired
  private BodyTypeRepository bodyTypeRepository;

  @Transactional
  public void addNewCarReferences(Brand brand, Color color, FuelType fuelType, TransmissionType transmissionType, BodyType bodyType) {
    // Добавление новой марки
    brandRepository.save(brand);

    // Добавление нового цвета
    colorRepository.save(color);

    // Добавление нового типа топлива
    fuelTypeRepository.save(fuelType);

    // Добавление нового типа трансмиссии
    transmissionTypeRepository.save(transmissionType);

    // Добавление нового типа кузова
    bodyTypeRepository.save(bodyType);
  }
}
