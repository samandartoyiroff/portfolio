package uz.tuit.portfolio.dto.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.EducationType;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class EducationUpdateDto {

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private EducationType educationType;

}
