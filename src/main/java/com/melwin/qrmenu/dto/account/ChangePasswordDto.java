package com.melwin.qrmenu.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangePasswordDto {
    private String phoneNumber;
    private String currentPassword;
    private String newPassword;
}
