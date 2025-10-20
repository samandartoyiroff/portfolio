package uz.tuit.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyDto {

    private Long id;
    @NotBlank
    private String code;
    @NotBlank
    private String email;

}

