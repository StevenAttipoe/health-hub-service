package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.dto.request.PatientSignUpDto;
import sea.nat.ashesi.healthhubservice.dto.response.PatientDto;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;
import sea.nat.ashesi.healthhubservice.utils.PatientConvertor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/patient")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientConvertor patientConvertor;

    @PostMapping("/signup")
    public ResponseEntity signUpPatient(@RequestBody PatientSignUpDto request) {
        return ResponseEntity.ok(patientService.signUpPatient(request));
    }

    @GetMapping("/get")
    public ResponseEntity<PatientDto> getPatient() {
        return ResponseEntity.ok(patientConvertor.convert(patientService.getPatient()));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getPatients(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "4") int pageSize,
            @RequestParam(defaultValue = "patientId") String sortBy) {

        Map<String, Object> response = new HashMap<>();
        List<PatientDto> patientDtoList = patientService.getPatients(pageNo, pageSize, sortBy);
        response.put("patients", patientDtoList);
        response.put("totalPages", patientService.getTotalPage(pageNo, pageSize, sortBy));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
