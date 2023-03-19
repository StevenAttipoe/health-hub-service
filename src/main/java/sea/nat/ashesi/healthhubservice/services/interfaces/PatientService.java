package sea.nat.ashesi.healthhubservice.services.interfaces;

import sea.nat.ashesi.healthhubservice.dto.request.PatientSignUpDto;
import sea.nat.ashesi.healthhubservice.dto.response.PatientDto;
import sea.nat.ashesi.healthhubservice.model.Patient;

import java.util.List;

public interface PatientService {

    String signUpPatient(PatientSignUpDto request);

    Patient getPatient();

    List<PatientDto> getPatients(int pageNo, int pageSize, String sortBy);

    int getTotalPage(int pageNo, int pageSize, String sortBy);
}
