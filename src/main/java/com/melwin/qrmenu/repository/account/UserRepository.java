package com.melwin.qrmenu.repository.account;

import com.melwin.qrmenu.dto.account.UserAndRestaurantProfileDto;
import com.melwin.qrmenu.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    public boolean existsUserByPhoneNumber(String phoneNumber);

    public boolean existsUserByEmail(String email);

    @Query("select (count(u) > 0) from User u where u.email = :email and u.phoneNumber <> :phoneNumber")
    public boolean existsByEmailAndPhoneNumberNot(@Param("email") String email, @Param("phoneNumber") String phoneNumber);

    @Query("select u.password from User u where u.phoneNumber = :phone")
    public String findPasswordByPhoneNumber(@Param("phone") String phoneNumber);

//    @Query("select u from User u where u.phoneNumber = :phoneNumber")
//    public User findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    public Optional<User> findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);


    @Query("select new com.melwin.qrmenu.dto.account.UserAndRestaurantProfileDto(u.id, u.phoneNumber, u.name, u.email, r.name, r.address, r.type) from User u join u.restaurant r where u.phoneNumber = :phoneNumber ")
    public UserAndRestaurantProfileDto findUserProfile(@Param("phoneNumber") String phoneNumber);

    public User findUserById(Long id);
}

