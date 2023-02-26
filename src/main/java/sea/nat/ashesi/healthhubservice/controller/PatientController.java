package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.dto.PatientDto;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.services.interfaces.PatientService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("api/v1/patient")
@AllArgsConstructor
public class PatientController {

    private PatientService patientService;

    @GetMapping("/getPatients")
    public ResponseEntity<Map<String, Object>> getPatients(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "4") int pageSize,
            @RequestParam(defaultValue = "patientId") String sortBy) {

        Map<String, Object> response = new HashMap<>();
        List<PatientDto> patientDtoList = patientService.getPatients(pageNo, pageSize, sortBy);
        response.put("content", patientDtoList);
        response.put("totalPages", patientService.getTotalPage(pageNo, pageSize, sortBy));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
