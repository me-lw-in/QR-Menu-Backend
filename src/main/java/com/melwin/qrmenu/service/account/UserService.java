package com.melwin.qrmenu.service.account;

import com.melwin.qrmenu.dto.account.UserAndRestaurantCreateDto;
import com.melwin.qrmenu.dto.account.UserAndRestaurantProfileDto;
import com.melwin.qrmenu.entity.Restaurant;
import com.melwin.qrmenu.entity.User;
import com.melwin.qrmenu.repository.account.RestaurantRepository;
import com.melwin.qrmenu.repository.account.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.melwin.qrmenu.enums.Role.OWNER;


@AllArgsConstructor
@Service   //testing
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserAndRestaurantProfileDto createUser(UserAndRestaurantCreateDto userAndRestaurantCreateDto) {

        if (
                userRepository.existsUserByPhoneNumber(userAndRestaurantCreateDto.getPhoneNumber())
                ||
                userRepository.existsUserByEmail(userAndRestaurantCreateDto.getEmail())) {
            return null;
        }

        var user = new User();
        var restaurant = new Restaurant();

        if (userAndRestaurantCreateDto.getEmail() != null && !userAndRestaurantCreateDto.getEmail().trim().isEmpty())
            user.setEmail(userAndRestaurantCreateDto.getEmail());

        user.setName(userAndRestaurantCreateDto.getName().trim());
        user.setPhoneNumber(userAndRestaurantCreateDto.getPhoneNumber().trim());
        user.setPassword(passwordEncoder.encode(userAndRestaurantCreateDto.getPassword()));
        user.setRole(OWNER);

        restaurant.setName(userAndRestaurantCreateDto.getRestaurantName().trim());
        restaurant.setAddress(userAndRestaurantCreateDto.getRestaurantAddress().trim());
        restaurant.setType(userAndRestaurantCreateDto.getType());
        restaurant.setIsOpen(true);

        user.addRestaurant(restaurant);

        userRepository.save(user);
        System.out.println("User & Restaurant created successfully!");
        return fetchProfile(userAndRestaurantCreateDto.getPhoneNumber());
    }

    public UserAndRestaurantProfileDto fetchProfile(String userName) {
        return userRepository.findUserProfile(userName);
    }

    @Transactional
    public void updateProfile(UserAndRestaurantProfileDto userData) {

        var user = userRepository.findUserByPhoneNumber(userData.getPhoneNumber().trim()).orElse(null);

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findUserByPhoneNumber(username).orElseThrow(
                ()->new UsernameNotFoundException("User Not Found!")
        );

        return new org.springframework.security.core.userdetails.User(
                user.getPhoneNumber(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
