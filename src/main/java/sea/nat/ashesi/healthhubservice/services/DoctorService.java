package sea.nat.ashesi.healthhubservice.services;

import sea.nat.ashesi.healthhubservice.model.Doctor;

public interface DoctorService {
    boolean signUpDoctor(Doctor doctor);

    String signInDoctor(Doctor doctor);

}
