package com.melwin.qrmenu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "otp_verifications")
public class OtpVerification {
    @Id
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "otp")
    private String otp;

    @Column(name = "generated_at")
    private Instant generatedAt;

}