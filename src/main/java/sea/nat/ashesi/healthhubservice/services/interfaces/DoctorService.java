package sea.nat.ashesi.healthhubservice.services.interfaces;

import sea.nat.ashesi.healthhubservice.dto.request.DoctorLogInDto;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorSignUpDto;
import sea.nat.ashesi.healthhubservice.model.Doctor;

public interface DoctorService {
    String signUpDoctor(DoctorSignUpDto request);

    String authenticateDoctor(DoctorLogInDto request);

    Doctor getDoctor(String email);

    Doctor getNextDoctor();

}
