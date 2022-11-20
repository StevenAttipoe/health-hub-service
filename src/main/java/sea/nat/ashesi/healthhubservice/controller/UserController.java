package sea.nat.ashesi.healthhubservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sea.nat.ashesi.healthhubservice.model.User;
import sea.nat.ashesi.healthhubservice.services.UserService;

@RequestMapping("api/v1/patients")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUpUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.signUpUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.loginUser(user), HttpStatus.OK);
    }
}
