package com.melwin.qrmenu.repository.account;

import com.melwin.qrmenu.entity.OtpVerification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OtpVerificationRepository extends CrudRepository<OtpVerification, String> {
    public boolean existsOtpVerificationByPhoneNumber(String phoneNumber);

    OtpVerification findOtpVerificationByPhoneNumber(String phoneNumber);
}