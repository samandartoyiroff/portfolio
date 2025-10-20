package uz.tuit.portfolio.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {

    public boolean validEmail(String email) {
        return email.matches("^[^@\\s]+@gmail\\.com$");
    }

    public boolean validPhone(String phone) {
        return phone.matches("^\\+998(90|91|93|94|95|97|88|99|33|50|70|71|55|69|62|61|75|76|77|72|73|74|65)\\d{7}$");

    }

    public boolean validPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password.matches(regex);
    }

    public boolean validUsername(String username) {
        return username.matches("^[A-Za-z][A-Za-z0-9]{5,}$");
    }

}
