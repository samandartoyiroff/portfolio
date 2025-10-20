package uz.tuit.portfolio.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.model.EducationType;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class CertificateCreateDto {

    @NotBlank
    private String name;

    private String description;


}
