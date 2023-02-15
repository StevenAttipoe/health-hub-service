package sea.nat.ashesi.healthhubservice.dto;

import lombok.Builder;
import lombok.Value;
import sea.nat.ashesi.healthhubservice.model.Sex;

import java.time.LocalDate;

@Builder
@Value
public class PatientDto {
    private String surname;
    private String firstNames;
    private String nationality;
    private Sex sex;
    private LocalDate dateOfBirth;
    private double height;
    private String personalIdNumber;
}
