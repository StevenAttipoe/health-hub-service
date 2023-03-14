package sea.nat.ashesi.healthhubservice.dto.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DoctorDto {
    private String name;
    private String email ;
    private int phoneNumber;
    private String speciality;

}
