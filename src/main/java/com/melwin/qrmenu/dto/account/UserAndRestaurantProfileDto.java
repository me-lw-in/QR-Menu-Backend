package com.melwin.qrmenu.dto.account;

import com.melwin.qrmenu.enums.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAndRestaurantProfileDto {
    private Long id;
    private String phoneNumber;
    private String name;
    private String email;
    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private RestaurantType type;
}
