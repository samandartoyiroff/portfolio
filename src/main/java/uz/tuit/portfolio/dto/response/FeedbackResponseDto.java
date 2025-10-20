package uz.tuit.portfolio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FeedbackResponseDto extends AuditableResponseDto {

    private Long id;

    private String fromFullName;

    private Long fromImageId;

    private String fromImageUrl;

    private String toFullName;

    private Long toImageId;

    private String toImageUrl;

    private String feedback;

}
