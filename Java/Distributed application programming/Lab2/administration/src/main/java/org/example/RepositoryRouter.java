package org.example;

import java.util.HashMap;
import java.util.Map;
import org.example.domain.Resident;
import org.example.repository.abaclia.AbacliaResidentRepository;
import org.example.repository.basarabeasca.BasarabeascaResidentRepository;
import org.example.repository.bascalia.BascaliaResidentRepository;
import org.example.repository.iserlia.IserliaResidentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryRouter {

  private final Map<String, JpaRepository<Resident, Long>> cityRepositories = new HashMap<>();

  public RepositoryRouter(
      AbacliaResidentRepository abacliaRepo,
      BasarabeascaResidentRepository basarabeascaRepo,
      IserliaResidentRepository iserliaRepo,
      BascaliaResidentRepository bascaliaRepo
  ) {
    cityRepositories.put("abaclia", abacliaRepo);
    cityRepositories.put("basarabeasca", basarabeascaRepo);
    cityRepositories.put("iserlia", iserliaRepo);
    cityRepositories.put("bascalia", bascaliaRepo);
  }

  public JpaRepository<Resident, Long> getRepository(String city) {
    return cityRepositories.get(city.toLowerCase());
  }
}
