package sea.nat.ashesi.healthhubservice.dto.request;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ExternalMedicalRecordDto {
    String personalIdNumber;
    String field;
    String value;
}
