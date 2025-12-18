package uz.tuit.portfolio.dto.response;


import lombok.*;
import uz.tuit.portfolio.model.HardSkillDegree;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HardSkillResponseDto {

    private Long id;

    private String name;

    private HardSkillDegree degree;

}
