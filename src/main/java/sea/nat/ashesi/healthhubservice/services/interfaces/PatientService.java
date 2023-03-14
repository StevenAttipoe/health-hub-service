package sea.nat.ashesi.healthhubservice.services.interfaces;

import sea.nat.ashesi.healthhubservice.dto.response.PatientDto;
import java.util.List;

public interface PatientService {

    List<PatientDto> getPatients(int pageNo, int pageSize, String sortBy);

    int getTotalPage(int pageNo, int pageSize, String sortBy);
}
