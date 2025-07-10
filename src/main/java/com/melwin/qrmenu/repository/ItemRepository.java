package com.melwin.qrmenu.repository;

import com.melwin.qrmenu.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {
  @Query("select i from Item i where i.createdBy.id = :userId and i.isDefault = false")
  public List<Item> findItemsByUserId(@Param("userId") Long userId);
}