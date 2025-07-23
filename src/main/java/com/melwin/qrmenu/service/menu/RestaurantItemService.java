package com.melwin.qrmenu.service.menu;

import com.melwin.qrmenu.dto.menu.create.ItemBlockDto;
import com.melwin.qrmenu.entity.Category;
import com.melwin.qrmenu.entity.Item;
import com.melwin.qrmenu.entity.Restaurant;
import com.melwin.qrmenu.entity.Restaurantitem;
import com.melwin.qrmenu.repository.account.RestaurantRepository;
import com.melwin.qrmenu.repository.menu.RestaurantitemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RestaurantItemService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantitemRepository restaurantitemRepository;

    public void linkItem(Long ownerId, Category category, Item item, ItemBlockDto itemBlockDto) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerId);

        var restaurantItemEntity = new Restaurantitem();
        restaurantItemEntity.setRestaurant(restaurant);
        restaurantItemEntity.setCategory(category);
        restaurantItemEntity.setItem(item);
        restaurantItemEntity.setPrice(itemBlockDto.getPrice());
        restaurantItemEntity.setIsAvailable(true);
        restaurantItemEntity.setDisplayOrder(itemBlockDto.getDisplayOrder());

        restaurantitemRepository.save(restaurantItemEntity);

    }
}
