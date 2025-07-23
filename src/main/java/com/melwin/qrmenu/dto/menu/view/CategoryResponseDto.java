package com.melwin.qrmenu.dto.menu.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String categoryName;
    private Boolean isDefault;
    private Integer displayOrder;
    List<ItemResponseDto> items;
}
