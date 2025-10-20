package uz.tuit.portfolio.service;

public interface EmailService {

    void sendVerificationEmail(String to, String code);


}
