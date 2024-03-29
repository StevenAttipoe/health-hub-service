package sea.nat.ashesi.healthhubservice.dto.response;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;


@Builder
@Value
public class MedicalRecordDto {
    long recordId;
    int pulseRate;
    int sysMmHg;
    int diaMmHg;
    String notes;
    boolean isChecked;
    LocalDate dateCreated;
    LocalTime timeCreated;
}
