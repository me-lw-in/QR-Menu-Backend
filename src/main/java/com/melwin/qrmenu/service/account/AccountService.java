package com.melwin.qrmenu.service.account;

import com.melwin.qrmenu.dto.account.ChangePasswordDto;
import com.melwin.qrmenu.dto.account.ChangeUsernameDto;
import com.melwin.qrmenu.entity.Category;
import com.melwin.qrmenu.entity.Item;
import com.melwin.qrmenu.entity.OtpVerification;
import com.melwin.qrmenu.repository.account.OtpVerificationRepository;
import com.melwin.qrmenu.repository.menu.CategoryRepository;
import com.melwin.qrmenu.repository.menu.ItemRepository;
import com.melwin.qrmenu.repository.account.UserRepository;
import com.melwin.qrmenu.service.otp.WhatsAppOtpService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Service
public class AccountService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final OtpVerificationRepository otpVerificationRepository;
    private final WhatsAppOtpService whatsAppOtpService;

    public void changePassword(ChangePasswordDto changePasswordDto) {
        var storedPassword = userRepository.findPasswordByPhoneNumber(changePasswordDto.getPhoneNumber().trim());

        if (!storedPassword.equals(changePasswordDto.getCurrentPassword()))
            throw new RuntimeException("Passwords don't match!");

        var user = userRepository.findUserByPhoneNumber(changePasswordDto.getPhoneNumber().trim()).orElse(null);

        user.setPassword(changePasswordDto.getNewPassword());
        userRepository.save(user);
        System.out.println("Password changed!");
    }

    public void changeUsername(ChangeUsernameDto changeUsernameDto) {

        if (!userRepository.existsUserByPhoneNumber(changeUsernameDto.getCurrentUsername().trim()))
            throw new RuntimeException("User not found!");

        var user = userRepository.findUserByPhoneNumber(changeUsernameDto.getCurrentUsername()).orElse(null);

        if (user.getPhoneNumber().equals(changeUsernameDto.getNewUsername().trim()))
            throw new IllegalArgumentException("New phone number cannot be the same as the current one.");

        user.setPhoneNumber(changeUsernameDto.getNewUsername());
        userRepository.save(user);
        System.out.println("Username changed!");

    }

    @Transactional
    public void softDelete(String username) {
        var user = userRepository.findUserByPhoneNumber(username).orElse(null);
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

    public void generateOtp(String phoneNumber) {
        String otp =String.valueOf(new Random().nextInt(9000)+1000);
        OtpVerification otpVerObj;
        if (!otpVerificationRepository.existsOtpVerificationByPhoneNumber(phoneNumber)){
            otpVerObj = new OtpVerification();
            otpVerObj.setPhoneNumber(phoneNumber);
            otpVerObj.setOtp(otp);
            otpVerObj.setGeneratedAt(Instant.now());
        }else{
            otpVerObj = otpVerificationRepository.findOtpVerificationByPhoneNumber(phoneNumber);
            otpVerObj.setOtp(otp);
            otpVerObj.setGeneratedAt(Instant.now());
        }
        otpVerificationRepository.save(otpVerObj);
        whatsAppOtpService.sendOtp(phoneNumber, otp);
        System.out.println("Otp is: " + otp);
    }

    public boolean verifyOtp(String phoneNumber, String toCheckOtp){
        var otpObject = otpVerificationRepository.findOtpVerificationByPhoneNumber(phoneNumber);
        String storedOtp = otpObject.getOtp();
        Instant storedTime = otpObject.getGeneratedAt().plus(Duration.ofMinutes(5));
        Instant currentTime = Instant.now();
        if(storedOtp.equals(toCheckOtp) && currentTime.isBefore(storedTime)){
            return true;
        }else{
            return false;
        }
    }
}

