package com.melwin.qrmenu.repository;

import com.melwin.qrmenu.entity.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    @Query("select r from Restaurant r where r.owner.id = :owner_id")
    public Restaurant findByOwnerId(@Param("owner_id") Long id);
}