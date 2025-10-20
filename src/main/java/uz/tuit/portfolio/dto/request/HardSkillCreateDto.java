package uz.tuit.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.JobType;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class HardSkillCreateDto {

    @NotBlank
    private String name;

}
