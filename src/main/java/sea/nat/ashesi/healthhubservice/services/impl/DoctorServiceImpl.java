package sea.nat.ashesi.healthhubservice.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorLogInDto;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorSignUpDto;
import sea.nat.ashesi.healthhubservice.dto.response.DoctorDto;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.model.Role;
import sea.nat.ashesi.healthhubservice.repositories.DoctorRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private int currentIndex = 0;

    @Override
    public String signUpDoctor(DoctorSignUpDto request) {
        var doctor = sea.nat.ashesi.healthhubservice.model.Doctor.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .experienceInYears(request.getExperienceInYears())
                .speciality(request.getSpeciality())
                .fullName(request.getFullName())
                .role(Role.USER)
                .build();
        doctorRepository.save(doctor);
        var jwtToken = jwtService.generateToken(doctor);
        return jwtToken;
    }

    @Override
    public String authenticateDoctor(DoctorLogInDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var doctor = doctorRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(doctor);
        return jwtToken;
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
        sea.nat.ashesi.healthhubservice.model.Doctor nextDoctor = null;
        int minPatients = Integer.MAX_VALUE;

        for (int i = 0; i < doctors.size(); i++) {
            sea.nat.ashesi.healthhubservice.model.Doctor doctor = doctors.get(i);
            int numPatients = doctor.getPatients().size();

            if (numPatients < minPatients) {
                nextDoctor = doctor;
                minPatients = numPatients;
            }
        }

        if (doctors.size() > 0) {
            currentIndex = (currentIndex + 1) % doctors.size();
        }
        return nextDoctor;
    }

}
