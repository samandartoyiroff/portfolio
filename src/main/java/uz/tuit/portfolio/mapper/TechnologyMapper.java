package uz.tuit.portfolio.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Technology;
import uz.tuit.portfolio.dto.response.TechnologyResponseDto;
import uz.tuit.portfolio.repository.TechnologyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TechnologyMapper {
    private final TechnologyRepository technologyRepository;

    public TechnologyResponseDto toResponseDto(Technology technology) {

        return new TechnologyResponseDto(

                technology.getId(),
                technology.getName(),
                technology.getLogo()!=null ? technology.getLogo().getPath() : null

        );

    }

    public List<Technology> toListEntity(List<Long> technologySkillIds) {

        return technologyRepository.findByIdIn(technologySkillIds);

    }

    public List<TechnologyResponseDto> toListResponse(List<Technology> technologies) {

        return technologies.stream().map(this::toResponseDto).collect(Collectors.toList());

    }
}
