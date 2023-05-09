package sea.nat.ashesi.healthhubservice.services.interfaces;

import sea.nat.ashesi.healthhubservice.dto.request.ExternalMedicalRecordDto;
import sea.nat.ashesi.healthhubservice.dto.response.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;

import java.util.List;
import java.util.Map;

public interface MedicalRecordService {
    MedicalRecord getRecentMedicalRecord(long patientId);

    MedicalRecord updateMedicalRecord(String annotation, long medicalRecordId);

    boolean approveMedicalRecord(long medicalRecordId);

    boolean createMedicalRecord(MedicalRecordDto request);

    boolean createExternalMedicalRecord(ExternalMedicalRecordDto request);

    List<MedicalRecordDto> getMedicalRecords(long patientId, int pageNo);

    List<MedicalRecordDto> getAllMedicalRecords(long patientId);


    Map<String, Integer> getMedicalRecordsByMonth(long doctorId);

    int getTotalPage(int pageNo, long patientId);

}
