package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Experience;
import uz.tuit.portfolio.dto.request.ExperienceCreateDto;
import uz.tuit.portfolio.dto.request.ExperienceUpdateDto;
import uz.tuit.portfolio.dto.response.ExperienceResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExperienceMapper {

    public ExperienceResponseDto toExperienceResponseDto(Experience experience) {

        ExperienceResponseDto experienceResponseDto = new ExperienceResponseDto();
        experienceResponseDto.setId(experience.getId());
        experienceResponseDto.setCompanyName(experience.getCompanyName());
        experienceResponseDto.setDescription(experience.getDescription());
        experienceResponseDto.setPosition(experience.getPosition());
        experienceResponseDto.setAddress(experience.getAddress());
        experienceResponseDto.setJobType(experience.getJobType());
        experienceResponseDto.setStartDate(experience.getStartDate());
        experienceResponseDto.setEndDate(experience.getEndDate());
        return experienceResponseDto;

    }

    public Experience toEntity(ExperienceCreateDto experienceCreateDto) {
        Experience experience = new Experience();
        experience.setCompanyName(experienceCreateDto.getCompanyName());
        experience.setDescription(experienceCreateDto.getDescription());
        experience.setAddress(experienceCreateDto.getAddress());
        experience.setPosition(experienceCreateDto.getPosition());
        experience.setJobType(experienceCreateDto.getJobType());
        experience.setStartDate(experienceCreateDto.getStartDate());
        experience.setEndDate(experienceCreateDto.getEndDate());
        return experience;
    }

    public List<Experience> toListEntity(List<ExperienceCreateDto> experience) {

        return experience.stream().map(this::toEntity).collect(Collectors.toList());

    }


    public List<ExperienceResponseDto> toListResponse(List<Experience> experiences) {

        return  experiences.stream().map(this::toExperienceResponseDto).collect(Collectors.toList());

    }

    public Experience updateById(ExperienceUpdateDto experienceUpdateDto, Experience experience) {


        if (experienceUpdateDto.getCompanyName() != null && !experienceUpdateDto.getCompanyName().isBlank()) {
            experience.setCompanyName(experienceUpdateDto.getCompanyName());
        }
        if (experienceUpdateDto.getDescription() != null &&  !experienceUpdateDto.getDescription().isBlank()) {
            experience.setDescription(experienceUpdateDto.getDescription());
        }
        if (experienceUpdateDto.getPosition() != null && !experienceUpdateDto.getPosition().isBlank()) {
            experience.setPosition(experienceUpdateDto.getPosition());
        }
        if (experienceUpdateDto.getJobType() != null ) {
            experience.setJobType(experienceUpdateDto.getJobType());
        }
        if (experienceUpdateDto.getStartDate() != null) {
            experience.setStartDate(experienceUpdateDto.getStartDate());
        }
        if (experienceUpdateDto.getEndDate() != null) {
            experience.setEndDate(experienceUpdateDto.getEndDate());
        }
        if (experienceUpdateDto.getAddress() != null && !experienceUpdateDto.getAddress().isBlank()) {
            experience.setAddress(experienceUpdateDto.getAddress());
        }

        return experience;

    }
}
