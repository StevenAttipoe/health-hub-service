package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.services.PatientService;

@RequestMapping("api/v1/patients")
@AllArgsConstructor
@RestController
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUpPatient(@RequestBody Patient patient) {
        return new ResponseEntity<>(patientService.signUpPatient(patient), HttpStatus.CREATED);
    }
}
