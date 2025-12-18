package uz.tuit.portfolio.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.*;
import uz.tuit.portfolio.dto.request.LanguageSkillCreateDto;
import uz.tuit.portfolio.dto.request.LanguageSkillUpdateDto;
import uz.tuit.portfolio.mapper.LanguageSkillMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.LanguageRepository;
import uz.tuit.portfolio.repository.LanguageSkillRepository;
import uz.tuit.portfolio.repository.PortfolioRepository;
import uz.tuit.portfolio.service.LanguageSkillService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageSkillServiceImpl implements LanguageSkillService {


    private final LanguageSkillMapper languageSkillMapper;
    private final LanguageSkillRepository languageSkillRepository;
    private final CVRepository cVRepository;
    private final LanguageRepository languageRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    @Transactional
    public ResponseEntity<?> addLanguageSkill(LanguageSkillCreateDto languageSkillCreateDto, User user, Long cvId) {

        CV cv;

        if (user != null) {
            cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }
        else {
            cv = cVRepository.findById(cvId).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }

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
    public ResponseEntity<?> update(Long id, LanguageSkillUpdateDto languageSkillUpdateDto, User user, Long cvId) {

        CV cv;

        if (user != null) {
            cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }
        else {
            cv = cVRepository.findById(cvId).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }

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
    public ResponseEntity<?> removeLanguageSkill(Long id, User user, Long cvId) {

        CV cv;

        if (user != null) {
            cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }
        else {
            cv = cVRepository.findById(cvId).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }

        LanguageSkill languageSkill1 = cv.getLanguageSkills().stream().filter(languageSkill -> languageSkill.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("This language skill does not belong this user"));
        List<LanguageSkill> languageSkills = cv.getLanguageSkills();

        languageSkills.remove(languageSkill1);
        cv.setLanguageSkills(languageSkills);
        cVRepository.save(cv);
        return ResponseEntity.ok("Removed language skill");

    }

    @Override
    @Transactional
    public ResponseEntity<?> addLanguageSkillToPortfolio(LanguageSkillCreateDto languageSkillCreateDto, User user) {

        Portfolio portfolio = user.getPortfolio();

        LanguageSkill languageSkill = languageSkillMapper.toEntity(languageSkillCreateDto);

        languageSkill.setPortfolio(portfolio);

        languageSkillRepository.save(languageSkill);

        List<LanguageSkill> languageSkills = portfolio.getLanguageSkills();

        languageSkills.add(languageSkill);

        portfolio.setLanguageSkills(languageSkills);

        portfolioRepository.save(portfolio);

        return ResponseEntity.ok(languageSkillMapper.toLanguageSkillResponseDto(languageSkill));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateLanguageSkillInPortfolio(Long id, LanguageSkillUpdateDto languageSkillUpdateDto, User user) {

        Portfolio portfolio = user.getPortfolio();

        LanguageSkill languageSkill1 = portfolio.getLanguageSkills().stream().filter(languageSkill -> languageSkill.getId()
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
    public ResponseEntity<?> removeLanguageSkillFromPortfolio(Long id, User user) {

        Portfolio portfolio = user.getPortfolio();

        LanguageSkill languageSkill1 = portfolio.getLanguageSkills().stream().filter(languageSkill -> languageSkill.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("This language skill does not belong this user"));
        List<LanguageSkill> languageSkills = portfolio.getLanguageSkills();

        languageSkills.remove(languageSkill1);
        portfolio.setLanguageSkills(languageSkills);
        portfolioRepository.save(portfolio);
        return ResponseEntity.ok("Removed language skill");
    }
}
