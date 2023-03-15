package sea.nat.ashesi.healthhubservice.services.interfaces;

import sea.nat.ashesi.healthhubservice.dto.request.PatientSignUpDto;
import sea.nat.ashesi.healthhubservice.model.Doctor;

public interface AuthenticationService {
    String signUpDoctor(Doctor request);

    String signUpPatient(PatientSignUpDto request);

    String authenticate(Doctor request);
}
