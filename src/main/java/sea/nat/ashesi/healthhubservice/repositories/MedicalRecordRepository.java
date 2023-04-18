package sea.nat.ashesi.healthhubservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    Optional<MedicalRecord> findFirstByPatientPatientIdOrderByDateCreatedDesc(@Param("patientId") long patientId);

    Page<MedicalRecord> findByPatientPatientId(Long patientId, Pageable pageable);

    int countByDateCreatedAndPatientDoctorDoctorId(LocalDate createdAt, long doctorId);

    int countByDateCreatedBetweenAndPatientDoctorDoctorId(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("doctorId") Long doctorId);

    @Query("SELECT COUNT(mr) FROM MedicalRecord mr WHERE mr.isChecked = true AND mr.patient.doctor.id = :doctorId")
    Long countCheckedMedicalRecordsForDoctor(@Param("doctorId") Long doctorId);

    @Query("SELECT COUNT(mr) FROM MedicalRecord mr WHERE mr.isChecked = false AND mr.patient.doctor.id = :doctorId")
    Long countUncheckedMedicalRecordsForDoctor(@Param("doctorId") Long doctorId);

}

