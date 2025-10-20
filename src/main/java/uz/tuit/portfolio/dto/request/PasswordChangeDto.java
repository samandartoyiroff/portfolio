package uz.tuit.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.annotations.ValidPassword;

@Setter
@Getter
@ToString
public class PasswordChangeDto {

    @NotBlank
    private String oldPassword;

    @ValidPassword
    private String newPassword;

}
