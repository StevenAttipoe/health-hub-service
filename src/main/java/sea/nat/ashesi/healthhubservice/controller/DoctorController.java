package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.config.JwtService;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorLogInDto;
import sea.nat.ashesi.healthhubservice.dto.request.DoctorSignUpDto;
import sea.nat.ashesi.healthhubservice.dto.response.DoctorDto;
import sea.nat.ashesi.healthhubservice.services.interfaces.DoctorService;

@RequestMapping("api/v1/doctor")
@AllArgsConstructor
@RestController
public class DoctorController {
    private final DoctorService doctorService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity authenticate(@RequestBody DoctorLogInDto request) {
        return ResponseEntity.ok(doctorService.authenticateDoctor(request));
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody DoctorSignUpDto request) {
        return ResponseEntity.ok(doctorService.signUpDoctor(request));
    }

    @GetMapping("/get")
    public ResponseEntity<DoctorDto> getDoctor(@RequestHeader("Authorization") String authorizationHeader ) {
        String token = authorizationHeader.substring(7);
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok(doctorService.getDoctor(email));
    }

}
