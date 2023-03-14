package sea.nat.ashesi.healthhubservice.services.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sea.nat.ashesi.healthhubservice.dto.response.DoctorDto;
import sea.nat.ashesi.healthhubservice.exception.UserException;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.repositories.DoctorRepository;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private int currentIndex = 0;

    @Override
    public DoctorDto getDoctor(String email) {
        Optional<Doctor> doctorOptional = doctorRepository.findByEmail(email);
        if(doctorOptional.isPresent()) {
            var doctor = doctorOptional.get();
            return DoctorDto.builder()
                    .name(doctor.getFullName())
                    .email(doctor.getEmail())
                    .phoneNumber(doctor.getPhoneNumber())
                    .speciality(doctor.getSpeciality())
                    .build();
        }
        throw new UserException("User does not exist");
    }

    @Override
    public Doctor getNextDoctor(){
        var doctors = doctorRepository.findAll();
        Doctor nextDoctor = null;
        int minPatients = Integer.MAX_VALUE;

        for (int i = 0; i < doctors.size(); i++) {
            Doctor doctor = doctors.get(i);
            int numPatients = doctor.getPatients().size();

            if (numPatients < minPatients) {
                nextDoctor = doctor;
                minPatients = numPatients;
            }
        }

        currentIndex = (currentIndex + 1) % doctors.size();
        return nextDoctor;
    }

}
