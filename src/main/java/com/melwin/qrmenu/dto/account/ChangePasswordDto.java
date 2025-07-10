package com.melwin.qrmenu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangePasswordDto {
    private String phoneNumber;
    private String currentPassword;
    private String newPassword;
}
