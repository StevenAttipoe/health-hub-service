package sea.nat.ashesi.healthhubservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;

import java.time.LocalDate;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    long countByDateCreated(LocalDate date);

}

