package sea.nat.ashesi.healthhubservice.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorLogInDto;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorSignUpDto;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.repositories.DoctorRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;
import sea.nat.ashesi.healthhubservice.utils.DoctorConvertor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorConvertor doctorConvertor;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String signUpDoctor(DoctorSignUpDto request) {
        Optional<Doctor> doctorEntity = doctorRepository.findByEmail(request.getEmail());

        if (doctorEntity.isEmpty()){
            var doctor = doctorConvertor.convert(request);
            return jwtService.generateToken(doctorRepository.save(doctor));
        }
        throw new UserException("This doctor already exists");
    }

    @Override
    public String authenticateDoctor(DoctorLogInDto request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var doctor = doctorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException("Doctor does not exist"));
        return jwtService.generateToken(doctor);
    }

    @Override
    public Doctor getDoctor(String email) {
        Optional<Doctor> doctorOptional = doctorRepository.findByEmail(email);
        if (doctorOptional.isPresent()) {
            return doctorOptional.get();
        }
        throw new UserException("User does not exist");
    }

    @Override
    public Doctor getNextDoctor(){
        var doctors = doctorRepository.findAll();
        Doctor nextDoctor = null;
        int minPatients = Integer.MAX_VALUE;

        for (Doctor doctor : doctors) {
            int numPatients = doctor.getPatients().size();

            if (numPatients < minPatients) {
                nextDoctor = doctor;
                minPatients = numPatients;
            }
        }
        return nextDoctor;
    }

}
