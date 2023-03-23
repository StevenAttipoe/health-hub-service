package sea.nat.ashesi.healthhubservice.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sea.nat.ashesi.healthhubservice.dto.response.DoctorDto;
import sea.nat.ashesi.healthhubservice.model.Doctor;

@Component
public class DoctorConvertor implements Converter<Doctor, DoctorDto>{

    @Override
    public DoctorDto convert(sea.nat.ashesi.healthhubservice.model.Doctor doctor) {
        return DoctorDto.builder()
                .name(doctor.getFullName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .speciality(doctor.getSpeciality())
                .build();
    }
}
