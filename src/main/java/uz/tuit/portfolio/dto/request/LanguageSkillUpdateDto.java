package uz.tuit.portfolio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.LangLevel;

@Setter
@Getter
@ToString
public class LanguageSkillUpdateDto {

    private Long languageId;

    private LangLevel level;

}
