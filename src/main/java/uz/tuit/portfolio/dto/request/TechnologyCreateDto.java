package uz.tuit.portfolio.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TechnologyCreateDto {

    @NotBlank
    private String name;

}
