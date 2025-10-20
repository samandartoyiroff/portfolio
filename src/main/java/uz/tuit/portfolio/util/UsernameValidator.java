package uz.tuit.portfolio.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.annotations.ValidUsername;


@Component
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        // Raqam bilan boshlanmasin, faqat harf va raqamlar, uzunligi kamida 6 ta
        return username.matches("^[A-Za-z][A-Za-z0-9]{5,}$");
    }
}
