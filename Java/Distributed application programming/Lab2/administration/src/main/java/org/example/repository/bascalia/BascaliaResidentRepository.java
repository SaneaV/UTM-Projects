package org.example.repository.bascalia;

import org.example.domain.Resident;
import org.example.repository.ResidentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BascaliaResidentRepository extends JpaRepository<Resident, Long>, ResidentRepository {

}
