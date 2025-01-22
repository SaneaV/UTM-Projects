package com.example.demo.repository;

import com.example.demo.model.Brand;
import com.example.demo.model.Car;
import com.example.demo.model.ReferenceStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Integer> {

  Optional<Car> findByLicensePlate(String licensePlate);

  List<Car> findByBrand(Brand brand);

  List<Car> findByStatus(ReferenceStatus status);

  @Query(value = "SELECT * FROM cars WHERE brandId = :brandId AND carModel = :carModel", nativeQuery = true)
  List<Car> findCarsByBrandAndModel(@Param("brandId") Byte brandId, @Param("carModel") String carModel);
}
