//package com.nsh.customerservice.services;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class OtpServiceTest {
//    @Mock
//     private OtpService otpService;
//
//    @Test
//    public void sendOtp(){
//        String email = "shaniraj914071@gmail.com";
//       otpService.sendOtp(email);
//       verify(otpService).sendOtp(email);
//    }
//
//    @Test
//    public void verifyEmail(){
//        String email = "shaniraj914071@gmail.com";
//    }
//
//}
package com.nsh.customerservice.services;

import com.nsh.customerservice.dao.OtpRepo;
import com.nsh.customerservice.entity.Otp;
import com.nsh.customerservice.exceptionhandler.exceptions.NotFoundException;
import com.nsh.customerservice.services.implementations.OtpServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OtpServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private OtpRepo otpRepo;

    @InjectMocks
    private OtpServiceImpl otpService;

    private String testEmail;
    private String testOtp;

//    @BeforeEach
//    void setUp() {
//        testEmail = "test@example.com";
//        testOtp = "12345678";
//    }

    @Test
    void sendOtp() {
        // Arrange
        ArgumentCaptor<Otp> otpCaptor = ArgumentCaptor.forClass(Otp.class);
        ArgumentCaptor<SimpleMailMessage> mailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Act
        otpService.sendOtp(testEmail);

        // Assert
        verify(mailSender, times(1)).send(mailCaptor.capture());
        verify(otpRepo, times(1)).save(otpCaptor.capture());

        Otp savedOtp = otpCaptor.getValue();
        SimpleMailMessage sentMail = mailCaptor.getValue();

        assertEquals(testEmail, savedOtp.getEmail());
        assertEquals(savedOtp.getOneTimePassword(), sentMail.getText());
        assertNotNull(savedOtp.getExpiresIn());
        assertTrue(savedOtp.getExpiresIn().after(new Date()));

        assertEquals("shaninavneet914071@gmail.com", sentMail.getFrom());
        assertEquals("shaniraj914071@gmail.com", sentMail.getTo()[0]);
        assertEquals("One Time Password", sentMail.getSubject());
    }

    @Test
    void verifyEmail_correctOtp() {
        // Arrange
        Date now = new Date();
        Otp otp = new Otp(testEmail, testOtp, now, new Date(now.getTime() + 5 * 60 * 1000));
        when(otpRepo.findByEmail(testEmail)).thenReturn(Optional.of(otp));

        // Act
        String result = otpService.verifyEmail(testEmail, testOtp);

        // Assert
        assertEquals(testEmail, result);
    }

    @Test
    void verifyEmail_incorrectOtp() {
        // Arrange
        Date now = new Date();
        Otp otp = new Otp(testEmail, "wrongOtp", now, new Date(now.getTime() + 5 * 60 * 1000));
        when(otpRepo.findByEmail(testEmail)).thenReturn(Optional.of(otp));

        // Act & Assert
        assertThrows(NotFoundException.class, () -> otpService.verifyEmail(testEmail, testOtp));
    }

    @Test
    void verifyEmail_expiredOtp() {
        // Arrange
        Date now = new Date();
        Otp otp = new Otp(testEmail, testOtp, now, new Date(now.getTime() - 1)); // expired OTP
        when(otpRepo.findByEmail(testEmail)).thenReturn(Optional.of(otp));

        // Act
        String result = otpService.verifyEmail(testEmail, testOtp);

        // Assert
        assertNull(result);
    }

    @Test
    void verifyEmail_noOtpFound() {
        // Arrange
        when(otpRepo.findByEmail(testEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> otpService.verifyEmail(testEmail, testOtp));
    }
}
