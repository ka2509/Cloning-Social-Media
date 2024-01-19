package com.example.newproject.controller;

import com.example.newproject.exception.UserException;
import com.example.newproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.newproject.response.AuthResponse;
import com.example.newproject.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> signUpUser(@RequestBody User user) {
        try {
            AuthResponse response = authService.signUpUser(user);

            // Return a ResponseEntity with HTTP status 201 (CREATED)
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserException userException) {
            // If a user with the same email already exists, return HTTP status 409 (CONFLICT)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new AuthResponse(userException.getMessage(), false));
        }
    }
    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@RequestBody User user) {
        try {
            AuthResponse response = authService.signIn(user);
            return new ResponseEntity<AuthResponse>(response,HttpStatus.ACCEPTED);
        } catch (UserException userException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(userException.getMessage(),false));
        }
    }
}
