package com.melwin.qrmenu.controller;

import com.melwin.qrmenu.dto.account.UserAndRestaurantCreateDto;
import com.melwin.qrmenu.dto.account.UserAndRestaurantProfileDto;
import com.melwin.qrmenu.repository.account.UserRepository;
import com.melwin.qrmenu.service.account.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    private final UserService userService;
    private final UserRepository userRepository;

    public TestController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/hello")
    public String sayHello(@RequestBody Map<String, String> data) {
        return "Hello " + data.get("name");
    }


}
