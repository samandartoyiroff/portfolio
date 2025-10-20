package uz.tuit.portfolio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.EducationType;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class EducationCreateDto {

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private EducationType educationType;

}
