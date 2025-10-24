package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.LanguageSkillCreateDto;
import uz.tuit.portfolio.dto.request.LanguageSkillUpdateDto;

public interface LanguageSkillService {
    ResponseEntity<?> addLanguageSkill(@Valid LanguageSkillCreateDto languageSkillCreateDto, User user, Long cvId);


    ResponseEntity<?> update(Long id, @Valid LanguageSkillUpdateDto languageSkillUpdateDto, User user, Long cvId);

    ResponseEntity<?> removeLanguageSkill(Long id, User user, Long cvId);

    ResponseEntity<?> addLanguageSkillToPortfolio(@Valid LanguageSkillCreateDto languageSkillCreateDto, User user);

    ResponseEntity<?> updateLanguageSkillInPortfolio(Long id, @Valid LanguageSkillUpdateDto languageSkillUpdateDto, User user);

    ResponseEntity<?> removeLanguageSkillFromPortfolio(Long id, User user);
}
