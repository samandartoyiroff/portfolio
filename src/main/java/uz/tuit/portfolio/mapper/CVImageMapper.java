package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Image;
import uz.tuit.portfolio.dto.response.CVImageResponseDto;

@Component
@RequiredArgsConstructor
public class CVImageMapper {

    public CVImageResponseDto toResponseDto(Image image) {

        if (image == null) return null;

        CVImageResponseDto cvImageResponseDto = new CVImageResponseDto();
        cvImageResponseDto.setImageId(image.getId());
        cvImageResponseDto.setImageName(image.getName());
        cvImageResponseDto.setImagePath(image.getPath());
        return cvImageResponseDto;



    };

}
