package uz.tuit.portfolio.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.annotations.ValidPhone;


@Component
public class PhoneNumberValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        // Valid Uzbekistan phone number: +998XXYYYYYYY
        return phoneNumber != null && phoneNumber.matches("^\\+?[1-9]\\d{1,14}$");
    }
}
