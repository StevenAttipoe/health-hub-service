package sea.nat.ashesi.healthhubservice.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorSignUpDto;
import sea.nat.ashesi.healthhubservice.dto.response.DoctorDto;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.model.Role;

@Component
@RequiredArgsConstructor
public class DoctorConvertor implements Converter<Doctor, DoctorDto>{
    private final PasswordEncoder passwordEncoder;

    @Override
    public DoctorDto convert(Doctor doctor) {
        return DoctorDto.builder()
                .name(doctor.getFullName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .speciality(doctor.getSpeciality())
                .build();
    }

    public Doctor convert(DoctorSignUpDto doctorSignUpDto) {
        return Doctor.builder()
                .email(doctorSignUpDto.getEmail())
                .password(passwordEncoder.encode(doctorSignUpDto.getPassword()))
                .gender(doctorSignUpDto.getGender())
                .phoneNumber(doctorSignUpDto.getPhoneNumber())
                .experienceInYears(doctorSignUpDto.getExperienceInYears())
                .speciality(doctorSignUpDto.getSpeciality())
                .fullName(doctorSignUpDto.getFullName())
                .role(Role.USER)
                .build();
    }
}
