package sea.nat.ashesi.healthhubservice.services.interfaces;

import sea.nat.ashesi.healthhubservice.dto.response.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecord getRecentMedicalRecord(long patientId);

    MedicalRecord updateMedicalRecord(String annotation, long medicalRecordId);

    boolean approveMedicalRecord(long medicalRecordId);

    boolean createMedicalRecord(MedicalRecordDto request);

    List<MedicalRecordDto> getMedicalRecords(long patientId, int pageNo);

    int getTotalPage(int pageNo, long patientId);

}
