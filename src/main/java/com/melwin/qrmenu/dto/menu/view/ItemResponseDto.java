package com.melwin.qrmenu.dto.menu.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@AllArgsConstructor
public class ItemResponseDto {
    private Long restaurantItemId;
    private String itemName;
    private Boolean isDefault;
    private Boolean isVeg;
    private BigDecimal price;
    private Boolean isAvailable;
    private String customName;
    private Integer displayOrder;
}
