package uz.tuit.portfolio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class TechnologyResponseDto {

    private Long id;

    private String name;

    private String logoLink;

}
