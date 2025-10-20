package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.SoftSkill;
import uz.tuit.portfolio.dto.response.SoftSkillResponseDto;
import uz.tuit.portfolio.repository.SoftSkillRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SoftSkillMapper {

    private final SoftSkillRepository softSkillRepository;

    public SoftSkillResponseDto toResponseDto(SoftSkill softSkill) {
        return new SoftSkillResponseDto(softSkill.getId(), softSkill.getName());
    }

    public List<SoftSkill> toListEntity(List<Long> softSkillIds) {

        return softSkillRepository.findByIdIn(softSkillIds);

    }

    public List<SoftSkillResponseDto> toListResponseDto(List<SoftSkill> softSkills) {
        return softSkills.stream().map(this::toResponseDto).collect(Collectors.toList());
    }

}
