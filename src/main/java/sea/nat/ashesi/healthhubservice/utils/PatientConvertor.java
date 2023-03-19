package sea.nat.ashesi.healthhubservice.utils;

import org.springframework.stereotype.Component;
import sea.nat.ashesi.healthhubservice.dto.response.PatientDto;
import sea.nat.ashesi.healthhubservice.model.Patient;
import org.springframework.core.convert.converter.Converter;

@Component
public class PatientConvertor implements Converter<Patient, PatientDto> {

    @Override
    public PatientDto convert(Patient patient) {
        return PatientDto.builder()
                .surname(patient.getSurname())
                .firstNames(patient.getFirstNames())
                .nationality(patient.getNationality())
                .gender(patient.getGender())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .height(patient.getHeight())
                .placeOfIssuance(patient.getPlaceOfIssuance())
                .assignedDoctor(patient.getDoctor().getFullName())
                .build();
    }
}
