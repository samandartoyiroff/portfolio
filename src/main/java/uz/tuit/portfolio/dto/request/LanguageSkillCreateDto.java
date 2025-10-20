package uz.tuit.portfolio.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.domain.Language;
import uz.tuit.portfolio.model.LangLevel;

@Setter
@Getter
@ToString
public class LanguageSkillCreateDto {

    private Long languageId;

    private LangLevel level;

}
