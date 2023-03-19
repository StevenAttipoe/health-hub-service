package sea.nat.ashesi.healthhubservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    long countByDateCreated(LocalDate date);

    @Query("SELECT mr FROM MedicalRecord mr JOIN mr.patient p WHERE p.id = :patientId ORDER BY mr.dateCreated DESC")
    Optional<MedicalRecord> findTopByPatientIdOrderByDateCreatedDesc(@Param("patientId") long patientId);
}

