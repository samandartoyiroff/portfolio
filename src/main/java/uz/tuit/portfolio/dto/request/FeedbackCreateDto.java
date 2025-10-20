package uz.tuit.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FeedbackCreateDto {

    @NotNull
    private Long toUser;

    @NotBlank
    private String feedback;

}
