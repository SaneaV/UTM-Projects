package org.example.repository.abaclia;

import org.example.domain.Resident;
import org.example.repository.ResidentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbacliaResidentRepository extends JpaRepository<Resident, Long>, ResidentRepository {

}
