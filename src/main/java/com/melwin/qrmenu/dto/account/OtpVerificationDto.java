package com.melwin.qrmenu.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OtpVerificationDto{
    private String phoneNumber;
    private String otp;
}
