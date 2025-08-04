package com.melwin.qrmenu.controller;

import com.melwin.qrmenu.dto.auth.LoginRequest;
import com.melwin.qrmenu.dto.jwt.JwtResponse;
import com.melwin.qrmenu.service.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping(path = "/login", produces = "application/json")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getPhoneNumber(),
                        loginRequest.getPassword()
                )
        );

        var token = jwtService.generateToken(loginRequest.getPhoneNumber());
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<String > handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
    }
}
