package com.example.demo.repository;

import com.example.demo.model.Car;
import com.example.demo.model.CarFeature;
import com.example.demo.model.CarFeatureId;
import com.example.demo.model.Feature;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarFeatureRepository extends JpaRepository<CarFeature, CarFeatureId> {

  List<CarFeature> findByCar(Car car);

  List<CarFeature> findByFeature(Feature feature);
}