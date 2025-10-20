package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Language;
import uz.tuit.portfolio.domain.LanguageSkill;
import uz.tuit.portfolio.dto.request.LanguageSkillCreateDto;
import uz.tuit.portfolio.dto.response.LanguageSkillResponseDto;
import uz.tuit.portfolio.repository.LanguageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LanguageSkillMapper {

    private final LanguageRepository languageRepository;

    public LanguageSkillResponseDto toLanguageSkillResponseDto(LanguageSkill languageSkill) {

        LanguageSkillResponseDto languageSkillResponseDto = new LanguageSkillResponseDto();
        languageSkillResponseDto.setId(languageSkill.getId());
        languageSkillResponseDto.setLanguage(languageSkill.getLanguage().getName());
        languageSkillResponseDto.setLangLevel(languageSkill.getLevel());
        return languageSkillResponseDto;

    }

    public LanguageSkill toEntity(LanguageSkillCreateDto languageSkillCreateDto) {

        LanguageSkill languageSkill = new LanguageSkill();

        Language language = languageRepository.findById(languageSkillCreateDto.getLanguageId())
                .orElseThrow(() -> new IllegalArgumentException("language id not found"));

        languageSkill.setLanguage(language);

        languageSkill.setLevel(languageSkillCreateDto.getLevel());

        return languageSkill;

    }

    public List<LanguageSkill> toListEntity(List<LanguageSkillCreateDto> languages) {

        return languages.stream().map(this::toEntity).collect(Collectors.toList());

    }

    public List<LanguageSkillResponseDto> toListResponse(List<LanguageSkill> languageSkills) {

        return languageSkills.stream().map(this::toLanguageSkillResponseDto).collect(Collectors.toList());

    }
}
