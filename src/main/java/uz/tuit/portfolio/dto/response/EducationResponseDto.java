package uz.tuit.portfolio.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.EducationType;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class EducationResponseDto {

    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private EducationType educationType;

    private Long certificateFileId;

    private String certificateFilePath;

}
