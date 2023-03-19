package sea.nat.ashesi.healthhubservice.dto.request;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DoctorLogInDto {
    String email ;
    String password;
}
