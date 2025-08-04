package com.melwin.qrmenu.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {
    String phoneNumber;
    String password;
}
