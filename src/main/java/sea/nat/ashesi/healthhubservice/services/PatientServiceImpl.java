package sea.nat.ashesi.healthhubservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.exception.PatientExistsException;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    @Override
    public boolean signUpPatient(Patient patient) {
        Optional<Patient> foundPatient = patientRepository.findByEmail(patient.getEmail());
        if(foundPatient.isPresent()) {
            throw new PatientExistsException("A patient with this email already exists");
        }
        patientRepository.save(patient);
        return true;
    }
}
