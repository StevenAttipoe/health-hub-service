package sea.nat.ashesi.healthhubservice.services.interfaces;

import sea.nat.ashesi.healthhubservice.dto.response.DoctorDto;
import sea.nat.ashesi.healthhubservice.model.Doctor;

public interface DoctorService {
    DoctorDto getDoctor(String email);

    Doctor getNextDoctor();

}
