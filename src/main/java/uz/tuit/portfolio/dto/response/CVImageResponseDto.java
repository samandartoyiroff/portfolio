package uz.tuit.portfolio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CVImageResponseDto {

    private Long imageId;

    private String imagePath;

    private String imageName;

}
