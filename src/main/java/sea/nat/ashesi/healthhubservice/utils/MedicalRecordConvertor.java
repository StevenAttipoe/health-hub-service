package sea.nat.ashesi.healthhubservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sea.nat.ashesi.healthhubservice.dto.response.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class MedicalRecordConvertor implements Converter<MedicalRecord, MedicalRecordDto>  {
    private final PatientService patientService;

    @Override
    public MedicalRecordDto convert(MedicalRecord medicalRecord) {
        return MedicalRecordDto.builder()
                .recordId(medicalRecord.getRecordId())
                .bloodPressure(medicalRecord.getBloodPressure())
                .glucoseLevel(medicalRecord.getGlucoseLevel())
                .pulseRate(medicalRecord.getPulseRate())
                .temperature(medicalRecord.getTemperature())
                .isChecked(medicalRecord.isChecked())
                .notes(medicalRecord.getNotes())
                .dateCreated(medicalRecord.getDateCreated())
                .timeCreated(medicalRecord.getTimeCreated())
                .build();
    }

    public MedicalRecord convert(MedicalRecordDto medicalRecordDto) {
        return MedicalRecord.builder()
                .bloodPressure(medicalRecordDto.getBloodPressure())
                .glucoseLevel(medicalRecordDto.getGlucoseLevel())
                .pulseRate(medicalRecordDto.getPulseRate())
                .temperature(medicalRecordDto.getTemperature())
                .patient(patientService.getPatient())
                .isChecked(false)
                .dateCreated(LocalDate.now())
                .timeCreated(LocalTime.now())
                .build();
    }

}
