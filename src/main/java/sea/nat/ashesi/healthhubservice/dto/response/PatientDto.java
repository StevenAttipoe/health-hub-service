package sea.nat.ashesi.healthhubservice.dto.response;

import lombok.Builder;
import lombok.Value;
import sea.nat.ashesi.healthhubservice.model.Gender;


@Builder
@Value
public class PatientDto {
    String surname;
    String firstNames;
    String nationality;
    Gender gender;
    String dateOfBirth;
    String height;
    String placeOfIssuance;
    String personalIdNumber;
    String assignedDoctor;
}
