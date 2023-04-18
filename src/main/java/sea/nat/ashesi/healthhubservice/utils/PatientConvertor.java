package sea.nat.ashesi.healthhubservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sea.nat.ashesi.healthhubservice.dto.request.PatientSignUpDto;
import sea.nat.ashesi.healthhubservice.dto.response.PatientDto;
import sea.nat.ashesi.healthhubservice.model.Patient;
import org.springframework.core.convert.converter.Converter;
import sea.nat.ashesi.healthhubservice.model.Role;
import sea.nat.ashesi.healthhubservice.services.impl.DoctorServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class PatientConvertor implements Converter<Patient, PatientDto> {
    private final DoctorServiceImpl doctorService;

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
                .personalIdNumber(patient.getPersonalIdNumber())
                .build();
    }

    public Patient convert(PatientSignUpDto patientSignUpDto) {
        return Patient.builder()
                .surname(patientSignUpDto.getSurname())
                .firstNames(patientSignUpDto.getFirstNames())
                .nationality(patientSignUpDto.getNationality())
                .gender(patientSignUpDto.getGender())
                .dateOfBirth(LocalDate.parse(patientSignUpDto.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .height(patientSignUpDto.getHeight())
                .placeOfIssuance(patientSignUpDto.getPlaceOfIssuance())
                .personalIdNumber(patientSignUpDto.getPersonalIdNumber())
                .signUpDate(LocalDate.now())
                .role(Role.USER)
                .doctor(doctorService.getNextDoctor())
                .build();
    }

}
