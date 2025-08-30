package com.melwin.qrmenu.repository.menu;


import com.melwin.qrmenu.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {
  @Query("select i from Item i where i.createdBy.id = :userId and i.isDefault = false")
  public List<Item> findItemsByUserId(@Param("userId") Long userId);

  public Item findItemByName(@Param("name") String name);

  @Query("select i from Item i where i.name = :itemName and i.createdBy.id = :ownerId")
  public Optional<Item> findItemByNameAndCreatedBy(@Param("itemName")String itemName, @Param("ownerId") Long ownerId);
}