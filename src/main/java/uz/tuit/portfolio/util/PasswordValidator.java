package uz.tuit.portfolio.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.annotations.ValidPassword;


@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password.length()>=8;
    }
}
