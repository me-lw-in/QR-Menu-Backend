package com.melwin.qrmenu.dto.menu.view;

import com.melwin.qrmenu.enums.RestaurantType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class FlatMenuRowDto {
    private String restauranteName;
    private RestaurantType type;
    private String address;
    private boolean isOpen;

    private Long categoryId;
    private String categoryName;
    private Boolean IsCategoryDefault;
    private Integer categoryDisplayOrder;

    private Long restaurantItemId;
    private String itemName;
    private Boolean IsItemDefault;
    private Boolean isVeg;
    private BigDecimal price;
    private Boolean isAvailable;
    private String customName;
    private Integer itemDisplayOrder;
}
