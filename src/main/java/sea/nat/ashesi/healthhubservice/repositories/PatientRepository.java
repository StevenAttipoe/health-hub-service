package sea.nat.ashesi.healthhubservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sea.nat.ashesi.healthhubservice.model.Patient;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    int countByDoctorDoctorId(Long doctorId);

    int countBySignUpDateAndDoctorDoctorId(LocalDate signUpDate, long doctorId);

    Optional<Patient> findByPersonalIdNumber(String personalIdNumber);

    Page<Patient> findByDoctorDoctorId(Long doctorId, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.doctor.id = :doctorId AND p.gender = '0'")
    int countMalePatientsByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.doctor.id = :doctorId AND p.gender = '1'")
    int countFemalePatientsByDoctorId(@Param("doctorId") Long doctorId);
}
