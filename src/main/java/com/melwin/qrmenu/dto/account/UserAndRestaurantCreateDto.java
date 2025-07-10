package com.melwin.qrmenu.dto.account;

import com.melwin.qrmenu.enums.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAndRestaurantCreateDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String restaurantName;
    private String restaurantAddress;
    private RestaurantType type;
}
