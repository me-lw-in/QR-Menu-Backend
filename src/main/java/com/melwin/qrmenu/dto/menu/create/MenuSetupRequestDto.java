package com.melwin.qrmenu.dto.menu.create;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MenuSetupRequestDto {
    private Long ownerId;
    private List<CategoryBlockDto> categories;
}
