package sea.nat.ashesi.healthhubservice.services.interfaces;

import sea.nat.ashesi.healthhubservice.dto.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;

public interface MedicalRecordService {
    MedicalRecord getMedicalRecord(long patientId);

    boolean createMedicalRecord(MedicalRecordDto request);

}
