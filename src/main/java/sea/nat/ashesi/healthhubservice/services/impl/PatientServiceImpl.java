package sea.nat.ashesi.healthhubservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.dto.response.PatientDto;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<PatientDto> getPatients(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Patient> patientsEntities = patientRepository.findAll(pageable);
        return patientsEntities
                .stream()
                .map(patient -> PatientDto.builder()
                        .fullName(patient.getFullName())
                        .dateOfBirth(patient.getDateOfBirth())
                        .sex(patient.getSex())
                        .height(patient.getHeight())
                        .nationality(patient.getNationality())
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
