package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.Language;
import uz.tuit.portfolio.domain.LanguageSkill;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.LanguageSkillCreateDto;
import uz.tuit.portfolio.dto.request.LanguageSkillUpdateDto;
import uz.tuit.portfolio.mapper.LanguageSkillMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.LanguageRepository;
import uz.tuit.portfolio.repository.LanguageSkillRepository;
import uz.tuit.portfolio.service.LanguageSkillService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageSkillServiceImpl implements LanguageSkillService {


    private final LanguageSkillMapper languageSkillMapper;
    private final LanguageSkillRepository languageSkillRepository;
    private final CVRepository cVRepository;
    private final LanguageRepository languageRepository;

    @Override
    @Transactional
    public ResponseEntity<?> addLanguageSkill(LanguageSkillCreateDto languageSkillCreateDto, User user) {

        CV cv = user.getCv();

        LanguageSkill languageSkill = languageSkillMapper.toEntity(languageSkillCreateDto);

        languageSkill.setCv(cv);

        languageSkillRepository.save(languageSkill);

        List<LanguageSkill> languageSkills = cv.getLanguageSkills();

        languageSkills.add(languageSkill);

        cv.setLanguageSkills(languageSkills);

        cVRepository.save(cv);

        return ResponseEntity.ok(languageSkillMapper.toLanguageSkillResponseDto(languageSkill));

    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Long id, LanguageSkillUpdateDto languageSkillUpdateDto, User user) {

        CV cv = user.getCv();
        LanguageSkill languageSkill1 = cv.getLanguageSkills().stream().filter(languageSkill -> languageSkill.getId()
                .equals(id)).findFirst().orElseThrow(() -> new RuntimeException("This language skill does not belong this user"));

        if (languageSkillUpdateDto.getLanguageId()!=null) {

            Language language = languageRepository.findById(languageSkillUpdateDto.getLanguageId())
                    .orElseThrow(() -> new RuntimeException("Language not found"));
            languageSkill1.setLanguage(language);
        }

        if (languageSkillUpdateDto.getLevel()!=null) {
            languageSkill1.setLevel(languageSkillUpdateDto.getLevel());
        }

        languageSkillRepository.save(languageSkill1);

        return ResponseEntity.ok(languageSkillMapper.toLanguageSkillResponseDto(languageSkill1));

    }

    @Override
    @Transactional
    public ResponseEntity<?> removeLanguageSkill(Long id, User user) {

        CV cv = user.getCv();
        LanguageSkill languageSkill1 = cv.getLanguageSkills().stream().filter(languageSkill -> languageSkill.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("This language skill does not belong this user"));
        List<LanguageSkill> languageSkills = cv.getLanguageSkills();

        languageSkills.remove(languageSkill1);
        cv.setLanguageSkills(languageSkills);
        cVRepository.save(cv);
        return ResponseEntity.ok("Removed language skill");

    }
}
