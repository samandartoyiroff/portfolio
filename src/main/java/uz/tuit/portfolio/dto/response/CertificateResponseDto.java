package uz.tuit.portfolio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CertificateResponseDto {

    private Long id;

    private String name;

    private String description;

    private Long certificateFileId;

    private String certificateFilePath;

}
