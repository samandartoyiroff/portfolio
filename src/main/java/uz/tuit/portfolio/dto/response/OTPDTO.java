package uz.tuit.portfolio.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class OTPDTO extends AuditableResponseDto {

    private Long id;

    private String email;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime expirationDate;

}
