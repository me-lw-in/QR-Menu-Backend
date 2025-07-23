package com.melwin.qrmenu.dto.menu.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ItemBlockDto {
    private String name;
    private Boolean isVeg;
    private BigDecimal price;
    private Boolean isNew;
    private Integer displayOrder;
}
