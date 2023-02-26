package sea.nat.ashesi.healthhubservice.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.PatientDto;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.repositories.DoctorRepository;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final JwtService jwtService;

    @Override
    public Doctor getDoctor(String jwt) {
        String doctorEmail = jwtService.extractUsername(jwt);
        Optional<Doctor> doctorOptional = doctorRepository.findByEmail(doctorEmail);
        if(doctorOptional.isPresent()) {
            return doctorOptional.get();
        }
        throw new UserException("User does not exist");
    }

}
