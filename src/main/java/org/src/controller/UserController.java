package org.src.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.model.User;
import org.src.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody final User user) {
        try {
            userService.signUp(user);
            return ResponseEntity.ok("Signup Successful! ");

        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Error: " + exception.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody final User loginDetails) {
        try {
            final User user = userService.signIn(loginDetails.getUsername(), loginDetails.getPassword());
            return ResponseEntity.ok(user);

        } catch (final RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(" Login Failed: " + exception.getMessage());
        }
    }
}
