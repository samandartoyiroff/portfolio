package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.LanguageSkillCreateDto;
import uz.tuit.portfolio.dto.request.LanguageSkillUpdateDto;

public interface LanguageSkillService {
    ResponseEntity<?> addLanguageSkill(@Valid LanguageSkillCreateDto languageSkillCreateDto, User user);


    ResponseEntity<?> update(Long id, @Valid LanguageSkillUpdateDto languageSkillUpdateDto, User user);

    ResponseEntity<?> removeLanguageSkill(Long id, User user);

}
