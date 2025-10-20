package uz.tuit.portfolio.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.EducationType;
import uz.tuit.portfolio.model.JobType;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class ExperienceCreateDto {

    @NotBlank
    private String companyName;

    @NotBlank
    private String position;

    private String description;

    private String address;

    private LocalDate startDate;

    private LocalDate endDate; // if null to present

    private JobType jobType;

}
