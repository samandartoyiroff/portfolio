package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.Experience;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.ExperienceCreateDto;
import uz.tuit.portfolio.dto.request.ExperienceUpdateDto;
import uz.tuit.portfolio.dto.response.ExperienceResponseDto;
import uz.tuit.portfolio.mapper.CVMapper;
import uz.tuit.portfolio.mapper.ExperienceMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.ExperienceRepository;
import uz.tuit.portfolio.service.ExperienceService;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;
    private final CVRepository cvRepository;
    private final CVMapper cvMapper;

    @Override
    @Transactional
    public ResponseEntity<ExperienceResponseDto> addExperience(ExperienceCreateDto experienceCreateDto, User user) {

        Experience experience = experienceMapper.toEntity(experienceCreateDto);

        CV cv = cvMapper.getCV(user);

        experience.setCv(cv);

        cv.getExperiences().add(experience);

        cvRepository.save(cv);

        ExperienceResponseDto response = experienceMapper.toExperienceResponseDto(experience);

        return ResponseEntity.ok(response);

    }

    @Override
    @Transactional
    public ResponseEntity<ExperienceResponseDto> updateExperience(Long id, ExperienceUpdateDto experienceUpdateDto) {

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found"));

        experience = experienceMapper.updateById(experienceUpdateDto, experience);

        experienceRepository.save(experience);

        return ResponseEntity.ok(experienceMapper.toExperienceResponseDto(experience));

    }

    @Override
    public ResponseEntity<?> delete(Long id, User user) {

        CV cv = user.getCv();

        System.out.println("Id: "+id);
        System.out.println("CV Id: "+cv.getId());
        experienceRepository.deleteByIdAndCVId(id, cv.getId());

        return ResponseEntity.ok("Experience deleted successfully");

    }
}
