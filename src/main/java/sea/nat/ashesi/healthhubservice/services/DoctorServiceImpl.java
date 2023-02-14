package sea.nat.ashesi.healthhubservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.repositories.DoctorRepository;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final JwtService jwtService;

    @Override
    public boolean signUpDoctor(Doctor doctor) {
        Optional<Doctor> foundUser = doctorRepository.findByEmail(doctor.getEmail());
        if(foundUser.isPresent()) {
            throw new UserException("A user with this email already exists");
        }
        doctorRepository.save(doctor);
        return true;
    }

    @Override
    public String signInDoctor(Doctor doctor) {
        Optional<Doctor> foundUser = doctorRepository.findByEmail(doctor.getEmail());
        if(foundUser.isPresent()) {
//            if(bCryptPasswordEncoder.matches(foundUser.get().getPassword(), doctor.getPassword())) {
                String token = Jwts.builder()
                        .setSubject(foundUser.get().getEmail())
                        .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                        .signWith(SignatureAlgorithm.HS512, "secretkey")
                        .compact();
//                response.addHeader("Authorization", "Bearer " + token);
                return token;
//            }
        }
        throw new UserException("User does not exist");
    }

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
