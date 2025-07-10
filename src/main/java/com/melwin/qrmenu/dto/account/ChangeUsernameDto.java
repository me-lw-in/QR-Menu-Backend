package com.melwin.qrmenu.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChangeUsernameDto {
    private String currentUsername;
    private String newUsername;
}
