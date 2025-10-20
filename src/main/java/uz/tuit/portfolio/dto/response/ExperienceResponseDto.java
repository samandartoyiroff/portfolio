package uz.tuit.portfolio.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.JobType;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class ExperienceResponseDto {

    private Long id;

    private String companyName;

    private String position;

    private String address;

    private String description;

    @JsonFormat(pattern = "YYYY/MM/dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "YYYY/MM/dd")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

}
