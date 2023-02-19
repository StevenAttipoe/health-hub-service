package sea.nat.ashesi.healthhubservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sea.nat.ashesi.healthhubservice.model.Patient;

import java.time.LocalDate;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    long countBySignUpDate(LocalDate date);
}
