package com.melwin.qrmenu.service.menu;

import com.melwin.qrmenu.dto.menu.create.CategoryBlockDto;
import com.melwin.qrmenu.entity.Category;
import com.melwin.qrmenu.entity.User;
import com.melwin.qrmenu.repository.account.UserRepository;
import com.melwin.qrmenu.repository.menu.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public Optional<Category> createCategory(CategoryBlockDto category, Long ownerId) {
        var newCategoryEntity = new Category();
        newCategoryEntity.setName(category.getCategoryName().trim());

        User owner = userRepository.findUserById(ownerId);
        newCategoryEntity.setCreatedBy(owner);

        newCategoryEntity.setIsDefault(false);
        newCategoryEntity.setDisplayOrder(category.getDisplayOrder());

        categoryRepository.save(newCategoryEntity);
        return Optional.of(newCategoryEntity);
    }
}
