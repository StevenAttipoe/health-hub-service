package sea.nat.ashesi.healthhubservice.dto.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DoctorDto {
    String name;
    String email ;
    int phoneNumber;
    String speciality;
}
