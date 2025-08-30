package com.melwin.qrmenu.service.menu;

import com.melwin.qrmenu.dto.menu.create.CategoryBlockDto;
import com.melwin.qrmenu.dto.menu.create.ItemBlockDto;
import com.melwin.qrmenu.dto.menu.create.MenuSetupRequestDto;
import com.melwin.qrmenu.dto.menu.view.CategoryResponseDto;
import com.melwin.qrmenu.dto.menu.view.FlatMenuRowDto;
import com.melwin.qrmenu.dto.menu.view.ItemResponseDto;
import com.melwin.qrmenu.dto.menu.view.MenuResponseDto;
import com.melwin.qrmenu.entity.Category;
import com.melwin.qrmenu.entity.Item;
import com.melwin.qrmenu.repository.menu.CategoryRepository;
import com.melwin.qrmenu.repository.menu.ItemRepository;
import com.melwin.qrmenu.repository.menu.RestaurantitemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class MenuService {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final RestaurantItemService restaurantItemService;
    private final RestaurantitemRepository restaurantItemRepository;

    @Transactional
    public void createMenu(MenuSetupRequestDto menuDto) {

        Long ownerId = menuDto.getOwnerId();

        List<CategoryBlockDto> categories = menuDto.getCategories();
        for (CategoryBlockDto categoryBlockDto : categories) {
            Optional<Category> categoryObj = categoryRepository.findCategoryByNameAndUser(categoryBlockDto.getCategoryName(), ownerId);

            if (categoryObj.isEmpty())
                categoryObj = categoryService.createCategory(categoryBlockDto, ownerId);

            for (ItemBlockDto itemBlockDto : categoryBlockDto.getItems()) {

                Optional<Item> item = itemRepository.findItemByNameAndCreatedBy(itemBlockDto.getName(), ownerId);
                if (item.isEmpty()) {
                    System.out.println("New!");
                    item = itemService.createItem(itemBlockDto, ownerId);
                    System.out.println("Existing item: " + item.get().getName());
                    System.out.println("Item created!");
                    restaurantItemService.linkItem(ownerId, categoryObj.get(), item.get(), itemBlockDto);
                    System.out.println("Restaurant Item Assigned!");
                }
            }
            System.out.println("Category created!");
        }
    }

    public MenuResponseDto getMenu(Long restaurantId) {

        List<FlatMenuRowDto> flatMenu = restaurantItemRepository.findMenyByRestaurantId(restaurantId);

        if (flatMenu.isEmpty()){
            return null;
        }

        Map<Long, CategoryResponseDto> categoryMap = new LinkedHashMap<>();

        for (FlatMenuRowDto flatMenuRowDto : flatMenu) {

            CategoryResponseDto category = categoryMap.get(flatMenuRowDto.getCategoryId());
            if (category == null) {
                category = new CategoryResponseDto(
                        flatMenuRowDto.getCategoryId(),
                        flatMenuRowDto.getCategoryName(),
                        flatMenuRowDto.getIsCategoryDefault(),
                        flatMenuRowDto.getCategoryDisplayOrder(),
                        new ArrayList<>());
                categoryMap.put(flatMenuRowDto.getCategoryId(), category);
            }

            ItemResponseDto item = new ItemResponseDto(
                    flatMenuRowDto.getRestaurantItemId(),
                    flatMenuRowDto.getItemName(),
                    flatMenuRowDto.getIsItemDefault(),
                    flatMenuRowDto.getIsVeg(),
                    flatMenuRowDto.getPrice(),
                    flatMenuRowDto.getIsAvailable(),
                    flatMenuRowDto.getCustomName(),
                    flatMenuRowDto.getItemDisplayOrder()
            );

            category.getItems().add(item);

        }

        MenuResponseDto menuResponseDto = new MenuResponseDto(
                flatMenu.getFirst().getRestauranteName(),
                flatMenu.getFirst().getType(),
                flatMenu.getFirst().getAddress(),
                flatMenu.getFirst().isOpen(),
                new ArrayList<>(categoryMap.values())
        );

        System.out.println("Menu ResponseDto: " + menuResponseDto);
        return menuResponseDto;
    }
}
