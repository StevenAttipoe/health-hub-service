package sea.nat.ashesi.healthhubservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sea.nat.ashesi.healthhubservice.dto.request.ExternalMedicalRecordDto;
import sea.nat.ashesi.healthhubservice.dto.response.MedicalRecordDto;
import sea.nat.ashesi.healthhubservice.model.ExternalMedicalRecord;
import sea.nat.ashesi.healthhubservice.model.MedicalRecord;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class ExternalMedicalRecordConvertor  implements Converter<ExternalMedicalRecordDto, ExternalMedicalRecord> {
    private final PatientService patientService;

    @Override
    public ExternalMedicalRecord convert(ExternalMedicalRecordDto externalMedicalRecord) {
        return ExternalMedicalRecord.builder()
                .field(externalMedicalRecord.getField())
                .value(externalMedicalRecord.getValue())
                .personalIdNumber(externalMedicalRecord.getPersonalIdNumber())
                .patient(patientService.getPatient(externalMedicalRecord.getPersonalIdNumber()))
                .isChecked(false)
                .dateCreated(LocalDate.now())
                .timeCreated(LocalTime.now())
                .build();
    }


}
