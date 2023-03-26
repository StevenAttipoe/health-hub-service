package sea.nat.ashesi.healthhubservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    long countByDateCreated(LocalDate date);

    Optional<MedicalRecord> findFirstByPatientPatientIdOrderByDateCreatedDesc(@Param("patientId") long patientId);

    Page<MedicalRecord> findByPatientPatientId(Long patientId, Pageable pageable);

}

