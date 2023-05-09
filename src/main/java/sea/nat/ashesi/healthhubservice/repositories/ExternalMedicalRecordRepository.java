package sea.nat.ashesi.healthhubservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sea.nat.ashesi.healthhubservice.model.ExternalMedicalRecord;

@Repository
public interface ExternalMedicalRecordRepository extends JpaRepository<ExternalMedicalRecord, Long> {
}
