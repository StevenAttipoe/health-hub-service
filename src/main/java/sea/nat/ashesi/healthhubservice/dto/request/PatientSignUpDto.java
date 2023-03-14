package sea.nat.ashesi.healthhubservice.dto.request;

import lombok.Builder;
import lombok.Value;
import sea.nat.ashesi.healthhubservice.model.Sex;

import java.time.LocalDate;

@Builder
@Value
public class PatientSignUpDto {
    private String surname;
    private String firstNames;
    private String nationality;
    private Sex sex;
    private String dateOfBirth;
    private double height;
    private String placeOfIssuance;

}
