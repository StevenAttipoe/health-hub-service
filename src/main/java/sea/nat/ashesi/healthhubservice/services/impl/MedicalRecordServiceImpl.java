package sea.nat.ashesi.healthhubservice.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.dto.request.ExternalMedicalRecordDto;
import sea.nat.ashesi.healthhubservice.dto.response.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.exception.MedicalRecordException;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;
import sea.nat.ashesi.healthhubservice.repositories.ExternalMedicalRecordRepository;
import sea.nat.ashesi.healthhubservice.repositories.MedicalRecordRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.MedicalRecordService;
import sea.nat.ashesi.healthhubservice.utils.ExternalMedicalRecordConvertor;
import sea.nat.ashesi.healthhubservice.utils.MedicalRecordConvertor;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final ExternalMedicalRecordRepository externalMedicalRecordRepository;
    private final MedicalRecordConvertor medicalRecordConvertor;
    private final ExternalMedicalRecordConvertor externalMedicalRecordConvertor;

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
    public MedicalRecord updateMedicalRecord(String annotation, long medicalRecordId) {
        Optional<MedicalRecord> medicalRecord =
                medicalRecordRepository.findById(medicalRecordId);

        if(medicalRecord.isPresent()) {
            var updatedRecord = medicalRecord.get();
            updatedRecord.setNotes(annotation);
            medicalRecordRepository.save(updatedRecord);
            return updatedRecord;
        }
        throw new MedicalRecordException("Record Not Found");
    }

    @Override
    public boolean approveMedicalRecord(long medicalRecordId) {
        Optional<MedicalRecord> medicalRecord =
                medicalRecordRepository.findById(medicalRecordId);

        if(medicalRecord.isPresent()) {
            var updatedRecord = medicalRecord.get();
            updatedRecord.setChecked(true);
            medicalRecordRepository.save(updatedRecord);
            return true;
        }
        throw new MedicalRecordException("Record Not Found");
    }

    @Override
    public boolean createMedicalRecord(MedicalRecordDto request) {
        medicalRecordRepository.save(medicalRecordConvertor.convert(request));
        return true;
    }

    @Override
    public boolean createExternalMedicalRecord(ExternalMedicalRecordDto externalMedicalRecordDto) {
        externalMedicalRecordRepository.save(Objects.requireNonNull(
                externalMedicalRecordConvertor.convert(externalMedicalRecordDto))
        );
        return true;
    }

    @Override
    public List<MedicalRecordDto> getMedicalRecords(long patientId, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 4, (Sort.by("isChecked")
                .and(Sort.by("dateCreated").descending()))
                .and(Sort.by("timeCreated").descending()));

        Page<MedicalRecord> medicalRecordsEntities = medicalRecordRepository.findByPatientPatientId(patientId, pageable);
        return medicalRecordsEntities
                .stream()
                .map(medicalRecordConvertor::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalRecordDto> getAllMedicalRecords(long patientId) {
        List<MedicalRecord> medicalRecordsEntities = medicalRecordRepository.findByPatientPatientId(patientId);
        return medicalRecordsEntities
                .stream()
                .map(medicalRecordConvertor::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> getMedicalRecordsByMonth(long doctorId) {
        Map<String, Integer> recordsByMonth = new LinkedHashMap<>();

        YearMonth currentYearMonth = YearMonth.now();

        for (int i = 1; i <= currentYearMonth.getMonthValue(); i++) {
            LocalDate startDate = LocalDate.of(currentYearMonth.getYear(), i, 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

            int count = medicalRecordRepository.countByDateCreatedBetweenAndPatientDoctorDoctorId(
                    startDate, endDate, doctorId);

            recordsByMonth.put(startDate.getMonth().toString(), count);
        }
        return recordsByMonth;
    }

    @Override
    public int getTotalPage(int pageNo, long patientId) {
        Pageable pageable = PageRequest.of(pageNo, 4, Sort.by("timeCreated"));
        Page<MedicalRecord> medicalRecordsEntities = medicalRecordRepository.findByPatientPatientId(patientId,pageable);
        return medicalRecordsEntities.getTotalPages();
    }


}
