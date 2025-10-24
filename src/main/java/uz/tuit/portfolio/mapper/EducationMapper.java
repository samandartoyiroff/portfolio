package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Education;
import uz.tuit.portfolio.domain.File;
import uz.tuit.portfolio.dto.request.EducationCreateDto;
import uz.tuit.portfolio.dto.request.EducationUpdateDto;
import uz.tuit.portfolio.dto.response.EducationResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EducationMapper {

    public EducationResponseDto educationToEducationDto(Education education) {

        EducationResponseDto educationResponseDto = new EducationResponseDto();

        educationResponseDto.setDescription(education.getDescription());

        educationResponseDto.setId(education.getId());

        educationResponseDto.setName(education.getEducationName());

        educationResponseDto.setStartDate(education.getStartDate());

        educationResponseDto.setEndDate(education.getEndDate());

        educationResponseDto.setEducationType(education.getEducationType());

        if (education.getCertificate()!=null) {

            File certificate = education.getCertificate();

            educationResponseDto.setCertificateFileId(certificate.getId());

            educationResponseDto.setCertificateFilePath(certificate.getPath());

        }

        return educationResponseDto;

    }

    public Education toEntity(EducationCreateDto educationCreateDto) {

        Education education = new Education();

        education.setEducationName(educationCreateDto.getName());
        education.setStartDate(educationCreateDto.getStartDate());
        education.setEndDate(educationCreateDto.getEndDate());
        education.setEducationType(educationCreateDto.getEducationType());
        education.setDescription(educationCreateDto.getDescription());

        return education;

    }

    public List<Education> toListEntity(List<EducationCreateDto> educations) {

        return educations.stream().map(this::toEntity).collect(Collectors.toList());

    }


    public List<EducationResponseDto> toListResponse(List<Education> educations) {

        return educations.stream().map(this::educationToEducationDto).collect(Collectors.toList());

    }

    public Education update(EducationUpdateDto educationUpdateDto, Education education) {

        if (educationUpdateDto.getName() != null && !educationUpdateDto.getName().isBlank()) {
            education.setEducationName(educationUpdateDto.getName());
        }
        if (educationUpdateDto.getStartDate() != null ) {
            education.setStartDate(educationUpdateDto.getStartDate());
        }

        if (educationUpdateDto.getDescription() != null && !educationUpdateDto.getDescription().isBlank()) {
            education.setDescription(educationUpdateDto.getDescription());
        }

        education.setEndDate(educationUpdateDto.getEndDate());

        if (educationUpdateDto.getEducationType() != null) {
            education.setEducationType(educationUpdateDto.getEducationType());
        }
        return education;

    }
}
