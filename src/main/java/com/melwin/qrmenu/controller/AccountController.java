package com.melwin.qrmenu.controller;

import com.melwin.qrmenu.dto.account.OtpRequestDto;
import com.melwin.qrmenu.dto.account.OtpVerificationDto;
import com.melwin.qrmenu.dto.account.UserAndRestaurantCreateDto;
import com.melwin.qrmenu.dto.account.UserAndRestaurantProfileDto;
import com.melwin.qrmenu.dto.jwt.JwtResponse;
import com.melwin.qrmenu.repository.account.UserRepository;
import com.melwin.qrmenu.service.account.AccountService;
import com.melwin.qrmenu.service.account.UserService;
import com.melwin.qrmenu.service.jwt.JwtService;
import com.melwin.qrmenu.service.otp.WhatsAppOtpService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Random;

@AllArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AccountService accountService;
    private final JwtService jwtService;
    private final WhatsAppOtpService whatsAppOtpService;

    @GetMapping("{phoneNumber}")
    public ResponseEntity<UserAndRestaurantProfileDto> getUserProfile(@PathVariable String phoneNumber) {
        var user = userService.fetchProfile(phoneNumber);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @RequestBody UserAndRestaurantCreateDto user,
            UriComponentsBuilder uriBuilder) {

        var userData = userService.createUser(user);

        if (userData == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email or Phone Number already exists!");
        }
        var uri = uriBuilder.path("/api/account/{phoneNumber}")
                .buildAndExpand(userData.getPhoneNumber())
                .toUri();
        var generatedToken = jwtService.generateToken(userData.getPhoneNumber());
        var restoProfile = userService.fetchProfile(userData.getPhoneNumber());
        var responseBody =  new Object(){
            public final String token = generatedToken;
            public final UserAndRestaurantProfileDto profile = restoProfile;
        };

        return ResponseEntity.created(uri).body(responseBody);
    }

    @PostMapping("/phone-otp/request")
    public ResponseEntity<String> generateOtp(@RequestBody OtpRequestDto body){
        String phoneNumber = body.getPhoneNumber();
        if (body.getPurpose().equals("REGISTER")) {
            if(userRepository.existsUserByPhoneNumber(phoneNumber)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already exists!");
            }else{
                accountService.generateOtp(phoneNumber);
                return ResponseEntity.ok().body("Otp has been generated!");
            }
        }else if (body.getPurpose().equals("RESET_PASSWORD")) {
            if(!userRepository.existsUserByPhoneNumber(phoneNumber)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number doesn't exists!");
            }else {
                accountService.generateOtp(phoneNumber);
                return ResponseEntity.ok().body("Otp has been generated!");
            }
        }else if (body.getPurpose().equals("CHANGE_PHONE")) {
            if(userRepository.existsUserByPhoneNumber(phoneNumber)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already exists!");
            }else {
                accountService.generateOtp(phoneNumber);
                return ResponseEntity.ok().body("Otp has been generated!");
            }
        }
        return null;
    }

    @PostMapping("/phone-otp/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationDto body){
        String phoneNumber = body.getPhoneNumber();
        String otp = body.getOtp();
        boolean isCorrect = accountService.verifyOtp(phoneNumber, otp);
        if (isCorrect) {
            return ResponseEntity.ok().body("Otp has been verified!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");
    }

    @GetMapping("/check-email")
    public ResponseEntity<String> verifyEmail(@RequestParam(name = "email") String email){
        boolean isUserExist = userRepository.existsUserByEmail(email);
        if (isUserExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!");
        }
        return ResponseEntity.ok().body("Email can be set!");
    }
}

