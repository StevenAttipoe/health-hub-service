package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.PatientDto;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;


@RequestMapping("api/v1/doctor")
@AllArgsConstructor
@RestController
public class DoctorController {

    private final DoctorService doctorService;
    private final JwtService jwtService;

    @GetMapping("/getMail")
    public ResponseEntity<String> getDoctorName(@RequestHeader("Authorization") String authorizationHeader ) {
        String token = authorizationHeader.substring(7);
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok(email);
    }

}
