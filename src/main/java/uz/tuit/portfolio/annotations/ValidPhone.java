package uz.tuit.portfolio.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = uz.tuit.portfolio.util.PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {

    String message() default "Phone number must start with +998 and be a valid Uzbekistan number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
