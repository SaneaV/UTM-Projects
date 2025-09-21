package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.domain.Resident;

public interface ResidentRepository {

  Optional<Resident> findById(Long id);

  List<Resident> findAll();

  void deleteById(Long id);

  Resident save(Resident r);
}
