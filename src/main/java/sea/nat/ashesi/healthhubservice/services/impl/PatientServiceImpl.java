package sea.nat.ashesi.healthhubservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.request.PatientSignUpDto;
import sea.nat.ashesi.healthhubservice.dto.response.PatientDto;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.model.Role;
import sea.nat.ashesi.healthhubservice.repositories.PatientRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;
import sea.nat.ashesi.healthhubservice.utils.PatientConvertor;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientConvertor patientConvertor;
    private final DoctorService doctorService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HttpServletRequest request;

    @Override
    public String signUpPatient(PatientSignUpDto request) {
        var patient = Patient.builder()
                .surname(request.getSurname())
                .firstNames(request.getFirstNames())
                .nationality(request.getNationality())
                .gender(request.getGender())
                .dateOfBirth(LocalDate.parse(request.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .height(request.getHeight())
                .placeOfIssuance(request.getPlaceOfIssuance())
                .personalIdNumber(request.getPersonalIdNumber())
                .signUpDate(LocalDate.now())
                .role(Role.USER)
                .doctor(doctorService.getNextDoctor())
                .build();

        Optional<Patient> patientOptional = patientRepository.findByPersonalIdNumber(request.getPersonalIdNumber());
        if (patientOptional.isEmpty()) {
            patientRepository.save(patient);
        }
        var jwtToken = jwtService.generateToken(patient);
        return jwtToken;
    }

    @Override
    public boolean authenticatePatient() {
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        return jwtService.isTokenValid(token, userDetails);
    }

    @Override
    public Patient getPatient() {
        String token = request.getHeader("Authorization").substring(7);
        String userName = jwtService.extractUsername(token);
        Optional<Patient> patientOptional = patientRepository.findByPersonalIdNumber(userName);
        if(patientOptional.isPresent()) {
           return patientOptional.get();
        }
        throw new UserException("User does not exist");
    }

    @Override
    public Patient getPatient(String patientId) {
        System.err.println(patientId);
        Optional<Patient> patientOptional = patientRepository.findByPersonalIdNumber(patientId);
        if(patientOptional.isPresent()) {
            return patientOptional.get();
        }
        throw new UserException("User does not exist");
    }

    @Override
    public List<PatientDto> getPatients(int pageNo, int pageSize, String sortBy) {
        String token = request.getHeader("Authorization").substring(7);
        var doctor = doctorService.getDoctor(jwtService.extractUsername(token));

        Page<Patient> patientsEntities = patientRepository.findByDoctorDoctorId(
                doctor.getDoctorId(),
                PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));

        return patientsEntities.stream().map(patientConvertor::convert).collect(Collectors.toList());
    }

    @Override
    public int getTotalPage(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Patient> patientsEntities = patientRepository.findAll(pageable);
        return patientsEntities.getTotalPages();
    }


}
