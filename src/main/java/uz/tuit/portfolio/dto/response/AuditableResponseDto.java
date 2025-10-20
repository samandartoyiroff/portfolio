package uz.tuit.portfolio.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString

public class AuditableResponseDto {

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime  createdDate;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime updatedDate;

    private Long createdBy;

    private Long updatedBy;

}
