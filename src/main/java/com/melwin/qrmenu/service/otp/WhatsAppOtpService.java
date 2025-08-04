package com.melwin.qrmenu.service.otp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppOtpService {
    @Value("${twilio.sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.token}")
    private String AUTH_TOKEN ;

    public void sendOtp(String phoneNumber, String otp) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+91" + phoneNumber),   // e.g. "whatsapp:+91XXXXXXXXXX"
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),       // Twilio Sandbox number
                otp + " is your OTP. It is valid for 5 minutes. Do not share it with anyone."
        ).create();

        System.out.println("OTP Sent! SID: " + message.getSid());
    }
}
