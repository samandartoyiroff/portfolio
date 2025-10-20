package uz.tuit.portfolio.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.service.EmailService;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendVerificationEmail(String to, String code) {


        String subject = "Sizga email tasdiqlash uchun kod yuborildi";
        String body = "VERIFICATION CODE: " + code;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

}
