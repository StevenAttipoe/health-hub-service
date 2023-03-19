package sea.nat.ashesi.healthhubservice.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class MedicalRecordDto {
    int pulseRate;
    int bloodPressure;
    int temperature;
    int glucoseLevel;
}
