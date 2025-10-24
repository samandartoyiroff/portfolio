package uz.tuit.portfolio.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.Experience;
import uz.tuit.portfolio.domain.Portfolio;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.ExperienceCreateDto;
import uz.tuit.portfolio.dto.request.ExperienceUpdateDto;
import uz.tuit.portfolio.dto.response.ExperienceResponseDto;
import uz.tuit.portfolio.mapper.ExperienceMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.ExperienceRepository;
import uz.tuit.portfolio.repository.PortfolioRepository;
import uz.tuit.portfolio.service.ExperienceService;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final CVRepository cvRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    @Transactional
    public ResponseEntity<ExperienceResponseDto> addExperience(ExperienceCreateDto experienceCreateDto, User user, Long cvId) {

        Experience experience = experienceMapper.toEntity(experienceCreateDto);

        CV cv = cvRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));

        experience.setCv(cv);

        cv.getExperiences().add(experience);

        cvRepository.save(cv);

        ExperienceResponseDto response = experienceMapper.toExperienceResponseDto(experience);

        return ResponseEntity.ok(response);

    }

    @Override
    @Transactional
    public ResponseEntity<ExperienceResponseDto> updateExperience(Long id, ExperienceUpdateDto experienceUpdateDto, Long cvId, User user) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found"));

        CV cv = cvRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));

        if (!experience.getCv().getId().equals(cv.getId())) throw new EntityNotFoundException("experience not belong to cv");

        experience = experienceMapper.updateById(experienceUpdateDto, experience);

        experienceRepository.save(experience);

        return ResponseEntity.ok(experienceMapper.toExperienceResponseDto(experience));

    }

    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id, User user, Long cvId) {

        CV cv = cvRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));

        Experience experience = experienceRepository.findByIdAndCvId(id, cv.getId()).orElseThrow(() -> new EntityNotFoundException("experience not found"));

        experienceRepository.deleteByIdAndCVId(id, cv.getId());

        return ResponseEntity.ok("Experience deleted successfully");

    }

    @Override
    @Transactional
    public ResponseEntity<ExperienceResponseDto> addExperienceToPortfolio(ExperienceCreateDto experienceCreateDto, User user) {

        Experience experience = experienceMapper.toEntity(experienceCreateDto);

        Portfolio portfolio = user.getPortfolio();

        experience.setPortfolio(portfolio);

        portfolio.getExperiences().add(experience);

        portfolioRepository.save(portfolio);

        ExperienceResponseDto response = experienceMapper.toExperienceResponseDto(experience);

        return ResponseEntity.ok(response);

    }

    @Override
    @Transactional
    public ResponseEntity<ExperienceResponseDto> updateExperienceInPortfolio(Long id, ExperienceUpdateDto experienceUpdateDto, User user) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found"));

        Portfolio portfolio = user.getPortfolio();

        if (!experience.getPortfolio().getId().equals(portfolio.getId())) throw new EntityNotFoundException("experience not belong to portfolio");

        experience = experienceMapper.updateById(experienceUpdateDto, experience);

        experienceRepository.save(experience);

        return ResponseEntity.ok(experienceMapper.toExperienceResponseDto(experience));

    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteFromPortfolio(Long id, User user) {

        Portfolio portfolio = user.getPortfolio();

        Experience experience = experienceRepository.findByIdAndPortfolioId(id, portfolio.getId()).orElseThrow(() -> new EntityNotFoundException("experience not found"));

        experienceRepository.deleteByIdAndPortfolioId(id, portfolio.getId());

        return ResponseEntity.ok("Experience deleted successfully");

    }
}
