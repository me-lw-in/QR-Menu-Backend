package com.melwin.qrmenu.dto.menu.view;

import com.melwin.qrmenu.enums.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class MenuResponseDto {
    private String restauranteName;
    private RestaurantType type;
    private String address;
    private boolean isOpen;
    List<CategoryResponseDto> categories;
}
