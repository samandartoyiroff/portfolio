package uz.tuit.portfolio.dto.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.JobType;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class ExperienceUpdateDto {

    private String companyName;

    private String position;

    private String description;

    private String address;

    private LocalDate startDate;

    private LocalDate endDate; // if null to present

    private JobType jobType;

}
