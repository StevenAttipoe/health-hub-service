package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.services.PatientService;

@RequestMapping("api/v1/patients")
@AllArgsConstructor
@RestController
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/signup")
    public Patient signUpPatient(@RequestBody Patient patient) {
        return patientService.signUpPatient(patient);
    }
}
