package com.nsh.customerservice.services.implementations;

import com.nsh.customerservice.dao.OtpRepo;
import com.nsh.customerservice.entity.Otp;
import com.nsh.customerservice.exceptionhandler.exceptions.NotFoundException;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private OtpRepo otpRepo;
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;
private static final String otpSubject = "Here's your One Time Password (OTP) - Expire in 5 minutes!";
    public void sendOtp(String email) {
        String OTP = RandomString.make(8);
        String content = "<p>Hello User </p>"
                + "<p>For security reason, you're required to use the following "
                + "One Time Password to login:</p>"
                + "<p><b>" + OTP + "</b></p>"
                + "<br>"
                + "<p>Note: this OTP is set to expire in 5 minutes.</p>";
        Date currentDate = new Date();

        Otp otp = new Otp(email, OTP, currentDate, new Date(currentDate.getTime() + OTP_VALID_DURATION));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shaninavneet914071@gmail.com");
        message.setTo("shaniraj914071@gmail.com");
        message.setSubject("One Time Password");
        message.setText(OTP);
        mailSender.send(message);
        otpRepo.save(otp);
        System.out.println("Message sent successfully");
    }
    public String verifyEmail(String email,String otp){
       Optional<Otp> realOtp = otpRepo.findByEmail(email);
       if(realOtp.isEmpty()){
           throw new NotFoundException("Wrong Otp !!");
       }
       else{
           if(otp.equals(realOtp.get().getOneTimePassword()) && new Date().after(realOtp.get().getExpiresIn()) ){
return null;
           }
       }
        return email;
    }
}