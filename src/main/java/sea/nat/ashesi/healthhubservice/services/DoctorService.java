package sea.nat.ashesi.healthhubservice.services;

import sea.nat.ashesi.healthhubservice.dto.PatientDto;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.model.Patient;

import java.util.List;

public interface DoctorService {
    Doctor getDoctor(String jwt);

    List<PatientDto> getAllPatients();
}
