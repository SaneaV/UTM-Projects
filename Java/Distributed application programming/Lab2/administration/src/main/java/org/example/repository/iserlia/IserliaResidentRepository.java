package org.example.repository.iserlia;

import org.example.domain.Resident;
import org.example.repository.ResidentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IserliaResidentRepository extends JpaRepository<Resident, Long>, ResidentRepository {

}
