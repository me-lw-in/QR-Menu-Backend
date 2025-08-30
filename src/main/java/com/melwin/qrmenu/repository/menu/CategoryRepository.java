package com.melwin.qrmenu.repository.menu;

import com.melwin.qrmenu.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("select c from Category c where c.createdBy.id = :userId and c.isDefault = false")
    public List<Category> findCategoryByUserId(@Param("userId") Long userId);


    @Query("select c from Category c where c.name = :name")
    public Category findCategoryByName(@Param("name") String name);

    @Query("select c from Category c where c.name = :categoryName and c.createdBy.id = :ownerId")
    public Optional<Category> findCategoryByNameAndUser(@Param("categoryName") String categoryName, @Param("ownerId") Long ownerId);
}