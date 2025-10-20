package uz.tuit.portfolio.util;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.annotations.ValidEmail;


@Component
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email.matches("^[^@\\s]+@gmail\\.com$");
    }
}
