package sea.nat.ashesi.healthhubservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.request.PatientSignUpDto;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.model.Role;
import sea.nat.ashesi.healthhubservice.repositories.DoctorRepository;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.AuthenticationService;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public String signUpDoctor(Doctor request) {
        var doctor = Doctor.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .sex(request.getSex())
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
    public String signUpPatient(PatientSignUpDto request) {
        var patient = Patient.builder()
                .fullName(request.getFirstNames() + " " +request.getSurname())
                .nationality(request.getNationality())
                .sex(request.getSex())
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .height((request.getHeight()))
                .placeOfIssuance(request.getPlaceOfIssuance())
                .signUpDate(LocalDate.now())
                .role(Role.USER)
                .doctor(doctorService.getNextDoctor())
                .build();

        patientRepository.save(patient);
        var jwtToken = jwtService.generateToken(patient);
        return jwtToken;
    }

    @Override
    public String authenticate(Doctor request) {
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
}
