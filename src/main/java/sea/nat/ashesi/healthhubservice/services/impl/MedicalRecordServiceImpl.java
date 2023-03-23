package sea.nat.ashesi.healthhubservice.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.dto.response.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.exception.MedicalRecordException;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;
import sea.nat.ashesi.healthhubservice.repositories.MedicalRecordRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.MedicalRecordService;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;
import sea.nat.ashesi.healthhubservice.utils.MedicalRecordConvertor;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordConvertor medicalRecordConvertor;
    private final PatientService patientService;

    @Override
    public MedicalRecord getRecentMedicalRecord(long patientId) {
        Optional<MedicalRecord> medicalRecord =
                medicalRecordRepository.findFirstByPatientPatientIdOrderByDateCreatedDesc(patientId);
        if (medicalRecord.isPresent()){
            return medicalRecord.get();
        }
        throw new MedicalRecordException("This patient does not have any records");
    }

    @Override
    public MedicalRecord updateMedicalRecord(String annotation) {
        return new MedicalRecord();
    }

    @Override
    public boolean createMedicalRecord(MedicalRecordDto request) {
        medicalRecordRepository.save(medicalRecordConvertor.convert(request));
        return true;
    }

    @Override
    public List<MedicalRecordDto> getMedicalRecords(long patientId) {
        Pageable pageable = PageRequest.of(0, 4, Sort.by("timeCreated"));

        Page<MedicalRecord> medicalRecordsEntities = medicalRecordRepository.findByPatientPatientId(patientId, pageable);

        return medicalRecordsEntities
                .stream()
                .map(medicalRecordConvertor::convert)
                .collect(Collectors.toList());
    }


}
