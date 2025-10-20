package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Occupation;
import uz.tuit.portfolio.dto.request.OccupationCreateDto;
import uz.tuit.portfolio.dto.response.OccupationResponseDto;
import uz.tuit.portfolio.repository.OccupationRepository;

@Component
@RequiredArgsConstructor
public class OccupationMapper {

    private final OccupationRepository occupationRepository;

    public OccupationResponseDto toResponseDto(Occupation occupation) {

        if (occupation == null) return null;

        OccupationResponseDto occupationResponseDto = new OccupationResponseDto();
        occupationResponseDto.setId(occupation.getId());
        occupationResponseDto.setName(occupation.getName());
        return occupationResponseDto;
    }

    public Occupation createOrGet(Long occupationId, String occupationName) {


        if (occupationId!=null) {
           return occupationRepository.findById(occupationId).orElseThrow(()->new IllegalArgumentException("occupationId not found"));
        }

        return toEntity(new OccupationCreateDto(occupationName));



    }

    public Occupation toEntity(OccupationCreateDto occupationCreateDto) {

        Occupation occupation = new Occupation();
        occupation.setName(occupationCreateDto.getOccupationName());
        occupationRepository.save(occupation);
        return occupation;

    }
}
