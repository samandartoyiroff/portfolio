package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.HardSkill;
import uz.tuit.portfolio.dto.response.HardSkillResponseDto;
import uz.tuit.portfolio.repository.HardSkillRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HardSkillMapper {

    private final HardSkillRepository hardSkillRepository;

    public HardSkillResponseDto toResponseDto(HardSkill hardSkill) {

        return new HardSkillResponseDto(hardSkill.getId(), hardSkill.getName());

    }

    public List<HardSkill> toListEntity(List<Long> hardSkillIds) {

        return hardSkillRepository.findByIdIn(hardSkillIds);

    }

    public List<HardSkillResponseDto> toListResponse(List<HardSkill> hardSkills) {

        return hardSkills.stream().map(this::toResponseDto).collect(Collectors.toList());

    }
}
