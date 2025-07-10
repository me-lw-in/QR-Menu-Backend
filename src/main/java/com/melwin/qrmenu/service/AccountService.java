package com.melwin.qrmenu.service;

import com.melwin.qrmenu.dto.ChangePasswordDto;
import com.melwin.qrmenu.dto.ChangeUsernameDto;
import com.melwin.qrmenu.entity.Category;
import com.melwin.qrmenu.entity.Item;
import com.melwin.qrmenu.repository.CategoryRepository;
import com.melwin.qrmenu.repository.ItemRepository;
import com.melwin.qrmenu.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;

    public void changePassword(ChangePasswordDto changePasswordDto) {
        var storedPassword = userRepository.findPasswordByPhoneNumber(changePasswordDto.getPhoneNumber().trim());

        if (!storedPassword.equals(changePasswordDto.getCurrentPassword()))
            throw new RuntimeException("Passwords don't match!");

        var user = userRepository.findUserByPhoneNumber(changePasswordDto.getPhoneNumber().trim());

        user.setPassword(changePasswordDto.getNewPassword());
        userRepository.save(user);
        System.out.println("Password changed!");
    }

    public void changeUsername(ChangeUsernameDto changeUsernameDto) {

        if (!userRepository.existsUserByPhoneNumber(changeUsernameDto.getCurrentUsername().trim()))
            throw new RuntimeException("User not found!");

        var user = userRepository.findUserByPhoneNumber(changeUsernameDto.getCurrentUsername());

        if (user.getPhoneNumber().equals(changeUsernameDto.getNewUsername().trim()))
            throw new IllegalArgumentException("New phone number cannot be the same as the current one.");

        user.setPhoneNumber(changeUsernameDto.getNewUsername());
        userRepository.save(user);
        System.out.println("Username changed!");

    }

    @Transactional
    public void softDelete(String username) {
        var user = userRepository.findUserByPhoneNumber(username);
        user.setIsActive(false);

        var restaurant = user.getRestaurant();
        restaurant.setIsActive(false);

        List<Category> categoryList = categoryRepository.findCategoryByUserId(user.getId());
        for (Category category : categoryList)
            category.setIsActive(false);

        List<Item> itemList = itemRepository.findItemsByUserId(user.getId());
        for (Item item : itemList)
            item.setIsActive(false);

        userRepository.save(user);
        categoryRepository.saveAll(categoryList);
        itemRepository.saveAll(itemList);
        System.out.println("User deleted!");
    }
}

