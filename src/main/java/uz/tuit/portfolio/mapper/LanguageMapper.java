package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Language;
import uz.tuit.portfolio.dto.response.LanguageResponseDto;

@Component
@RequiredArgsConstructor
public class LanguageMapper {

    public LanguageResponseDto languageToLanguageDto(Language language) {
        LanguageResponseDto languageDto = new LanguageResponseDto();
        languageDto.setId(language.getId());
        languageDto.setName(language.getName());
        return languageDto;
    }

}
