package sea.nat.ashesi.healthhubservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.dto.response.PatientDto;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientDto getPatient(String personalIdNumber) {
        Optional<Patient> patientOptional = patientRepository.findByPersonalIdNumber(personalIdNumber);
        if(patientOptional.isPresent()) {
            var patient = patientOptional.get();
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
        throw new UserException("User does not exist");
    }

    @Override
    public List<PatientDto> getPatients(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Patient> patientsEntities = patientRepository.findAll(pageable);
        return patientsEntities
                .stream()
                .map(patient -> PatientDto.builder()
                    .surname(patient.getSurname())
                    .firstNames(patient.getFirstNames())
                    .nationality(patient.getNationality())
                    .gender(patient.getGender())
                    .dateOfBirth(patient.getDateOfBirth().toString())
                    .height(patient.getHeight())
                    .placeOfIssuance(patient.getPlaceOfIssuance())
                    .build())
            .collect(Collectors.toList());
    }

    @Override
    public int getTotalPage(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Patient> patientsEntities = patientRepository.findAll(pageable);
        return patientsEntities.getTotalPages();
    }


}
