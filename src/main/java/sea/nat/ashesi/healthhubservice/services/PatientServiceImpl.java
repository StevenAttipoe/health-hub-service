package sea.nat.ashesi.healthhubservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    @Override
    public Patient signUpPatient(Patient patient) {
        patientRepository.save(patient);
        return patient;
    }
}
