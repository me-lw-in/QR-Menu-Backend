package com.melwin.qrmenu.service.account;

import com.melwin.qrmenu.dto.account.UserAndRestaurantCreateDto;
import com.melwin.qrmenu.dto.account.UserAndRestaurantProfileDto;
import com.melwin.qrmenu.entity.Restaurant;
import com.melwin.qrmenu.entity.User;
import com.melwin.qrmenu.repository.account.RestaurantRepository;
import com.melwin.qrmenu.repository.account.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.melwin.qrmenu.enums.Role.OWNER;


@AllArgsConstructor
@Service   //testing
public class UserService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void createUser(UserAndRestaurantCreateDto userAndRestaurantCreateDto) {

        if (userRepository.existsUserByPhoneNumber(userAndRestaurantCreateDto.getPhoneNumber()))
            throw new IllegalArgumentException("User with phone number already exists");

        var user = new User();
        var restaurant = new Restaurant();

        if (userAndRestaurantCreateDto.getEmail() != null && !userAndRestaurantCreateDto.getEmail().trim().isEmpty())
            user.setEmail(userAndRestaurantCreateDto.getEmail());

        user.setName(userAndRestaurantCreateDto.getName().trim());
        user.setPhoneNumber(userAndRestaurantCreateDto.getPhoneNumber().trim());
        user.setPassword(userAndRestaurantCreateDto.getPassword());
        user.setRole(OWNER);

        restaurant.setName(userAndRestaurantCreateDto.getRestaurantName().trim());
        restaurant.setAddress(userAndRestaurantCreateDto.getRestaurantAddress().trim());
        restaurant.setType(userAndRestaurantCreateDto.getType());

        user.addRestaurant(restaurant);

        userRepository.save(user);
        System.out.println("User & Restaurant created successfully!");
    }

    public void fetchProfile(String userName) {
        UserAndRestaurantProfileDto user = userRepository.findUserProfile(userName);

        if (user == null)
            throw new RuntimeException("User not found");

        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Restaurant Name: " + user.getRestaurantName());
        System.out.println("Restaurant Address: " + user.getRestaurantAddress());
        System.out.println("Type: " + user.getType());
    }

    @Transactional
    public void updateProfile(UserAndRestaurantProfileDto userData) {

        var user = userRepository.findUserByPhoneNumber(userData.getPhoneNumber().trim());

        if (userRepository.existsByEmailAndPhoneNumberNot(userData.getEmail().trim(), userData.getPhoneNumber()))
            throw new IllegalArgumentException("Email already in use by another user");

        var restaurant = restaurantRepository.findByOwnerId(user.getId());

        if (userData.getName() != null && !userData.getName().trim().isEmpty())
            user.setName(userData.getName().trim());

        if (userData.getEmail() != null && !userData.getEmail().trim().isEmpty())
            user.setEmail(userData.getEmail().trim());

        if (userData.getRestaurantName() != null && !userData.getRestaurantName().trim().isEmpty())
            restaurant.setName(userData.getRestaurantName().trim());

        if (userData.getRestaurantAddress() != null && !userData.getRestaurantAddress().trim().isEmpty())
            restaurant.setAddress(userData.getRestaurantAddress().trim());

        if (userData.getType() != null)
            restaurant.setType(userData.getType());

        user.addRestaurant(restaurant);

        userRepository.save(user);
        System.out.println("User/Restaurant details updated successfully!");
    }
}
