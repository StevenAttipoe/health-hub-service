package sea.nat.ashesi.healthhubservice.dto.request;

import lombok.Builder;
import lombok.Value;
import sea.nat.ashesi.healthhubservice.model.Gender;

@Builder
@Value
public class DoctorSignUpDto {
    String fullName;
    String email ;
    String password;
    Gender gender;
    int phoneNumber;
    String speciality;
    int experienceInYears;
}
