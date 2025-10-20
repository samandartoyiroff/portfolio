package uz.tuit.portfolio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.annotations.ValidPassword;

@Setter
@Getter
@ToString
public class PasswordRecoverDto {

    private String email;

    @ValidPassword
    private String newPassword;


}
