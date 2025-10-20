package uz.tuit.portfolio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.LangLevel;

@Setter
@Getter
@ToString
public class LanguageSkillResponseDto {

    private Long id;

    private String language;

    private LangLevel langLevel;

}
