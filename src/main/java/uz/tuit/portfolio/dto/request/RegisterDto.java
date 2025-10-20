package uz.tuit.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.Gender;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class RegisterDto {

    @NotBlank
    private String fullName;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private Gender gender;

}
