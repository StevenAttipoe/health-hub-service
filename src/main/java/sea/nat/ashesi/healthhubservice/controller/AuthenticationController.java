package sea.nat.ashesi.healthhubservice.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.services.interfaces.AuthenticationService;
import sea.nat.ashesi.healthhubservice.dto.request.PatientSignUpDto;
import sea.nat.ashesi.healthhubservice.model.Doctor;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/doctor/signup")
    public ResponseEntity signUpDoctor(@RequestBody Doctor request) {
        return ResponseEntity.ok(authenticationService.signUpDoctor(request));
    }

    @PostMapping("/patient/signup")
    public ResponseEntity signUpPatient(@RequestBody PatientSignUpDto request) {
        return ResponseEntity.ok(authenticationService.signUpPatient(request));
    }

    @PostMapping("/doctor/authenticate")
    public ResponseEntity authenticate(@RequestBody Doctor request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
