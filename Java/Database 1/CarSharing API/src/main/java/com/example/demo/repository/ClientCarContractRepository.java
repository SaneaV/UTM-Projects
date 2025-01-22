package com.example.demo.repository;

import com.example.demo.model.Car;
import com.example.demo.model.Client;
import com.example.demo.model.ClientCarContract;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCarContractRepository extends JpaRepository<ClientCarContract, Long> {

  List<ClientCarContract> findByClient(Client client);

  List<ClientCarContract> findByCar(Car car);

  List<ClientCarContract> findByOrderDateBetween(Date startDate, Date endDate);

  List<ClientCarContract> findByStartDateBetween(Date startDate, Date endDate);

  List<ClientCarContract> findByEndDateBetween(Date startDate, Date endDate);
}