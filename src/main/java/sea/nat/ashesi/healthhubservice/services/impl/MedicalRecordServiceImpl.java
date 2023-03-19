package sea.nat.ashesi.healthhubservice.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.dto.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.exception.MedicalRecordException;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;
import sea.nat.ashesi.healthhubservice.repositories.MedicalRecordRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.MedicalRecordService;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientService patientService;

    @Override
    public MedicalRecord getMedicalRecord(long patientId) {
        Optional<MedicalRecord> medicalRecord =
                medicalRecordRepository.findTopByPatientIdOrderByDateCreatedDesc(patientId);

        if (medicalRecord.isPresent()){
            return medicalRecord.get();
        }
        throw new MedicalRecordException("This patient does not have any records");
    }

    @Override
    public boolean createMedicalRecord(MedicalRecordDto request) {
        var medicalRecord = MedicalRecord.builder()
                        .bloodPressure(request.getBloodPressure())
                        .glucoseLevel(request.getGlucoseLevel())
                        .pulseRate(request.getPulseRate())
                        .temperature(request.getTemperature())
                        .patient(patientService.getPatient())
                        .dateCreated(LocalDate.now())
                        .build();

        medicalRecordRepository.save(medicalRecord);
        return true;
    }


}
