package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.model.Doctor;
import sea.nat.ashesi.healthhubservice.services.DoctorService;

@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("api/v1/doctor")
@AllArgsConstructor
@RestController
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUpDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.signUpDoctor(doctor), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logInDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.signInDoctor(doctor), HttpStatus.OK);
    }
}
