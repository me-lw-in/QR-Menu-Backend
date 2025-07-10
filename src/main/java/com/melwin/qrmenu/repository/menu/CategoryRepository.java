package com.melwin.qrmenu.repository;

import com.melwin.qrmenu.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query("select c from Category c where c.createdBy.id = :userId and c.isDefault = false")
    public List<Category> findCategoryByUserId(@Param("userId") Long userId);
}