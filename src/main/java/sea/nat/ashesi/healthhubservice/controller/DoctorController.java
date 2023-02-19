package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.PatientDto;
import sea.nat.ashesi.healthhubservice.model.Patient;
import sea.nat.ashesi.healthhubservice.services.DoctorService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("api/v1/doctor")
@AllArgsConstructor
@RestController
public class DoctorController {

    private final DoctorService doctorService;
    private final JwtService jwtService;

    @GetMapping("/getMail")
    public ResponseEntity<String> getDoctorName(@RequestHeader("Authorization") String authorizationHeader ) {
        String token = authorizationHeader.substring(7); // assuming the token is in the "Authorization" header preceded by "Bearer "
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok(email);
    }

    @GetMapping("/getAllPatients")
    public List<PatientDto> getAllPatients(){
        return doctorService.getAllPatients();
    }

}
