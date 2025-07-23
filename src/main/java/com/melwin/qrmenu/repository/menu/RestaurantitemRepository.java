package com.melwin.qrmenu.repository.menu;

import com.melwin.qrmenu.dto.menu.view.FlatMenuRowDto;
import com.melwin.qrmenu.dto.menu.view.MenuResponseDto;
import com.melwin.qrmenu.entity.Restaurantitem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantitemRepository extends CrudRepository<Restaurantitem, Long> {
    @Query("select new com.melwin.qrmenu.dto.menu.view.FlatMenuRowDto(" +
            "resto.name, resto.type, resto.address, resto.isOpen, " +
            "c.id, c.name, c.isDefault, c.displayOrder, " +
            "r.id, i.name, i.isDefault, i.isVeg, r.price, r.isAvailable, r.customName, r.displayOrder) " +
            "from Restaurantitem r " +
            "LEFT join r.restaurant resto " +
            "LEFT join r.item i " +
            "LEFT join r.category c " +
            "where r.restaurant.id = :restaurantId")
    public List<FlatMenuRowDto> findMenyByRestaurantId(@Param("restaurantId") Long restaurantId);
}