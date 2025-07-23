package com.melwin.qrmenu.dto.menu.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CategoryBlockDto {
    private String categoryName;
    private Integer displayOrder;
    private Boolean isNew;
    private List<ItemBlockDto> items;
}
