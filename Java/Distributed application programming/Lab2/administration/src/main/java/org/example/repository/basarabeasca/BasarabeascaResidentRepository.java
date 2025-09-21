package org.example.repository.basarabeasca;

import org.example.domain.Resident;
import org.example.repository.ResidentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasarabeascaResidentRepository extends JpaRepository<Resident, Long>, ResidentRepository {

}
