package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.EducationCreateDto;
import uz.tuit.portfolio.dto.request.EducationUpdateDto;
import uz.tuit.portfolio.dto.response.EducationResponseDto;

public interface EducationService {
    ResponseEntity<EducationResponseDto> addEducation(User user, @Valid EducationCreateDto educationCreateDto, MultipartFile educationFile);

    ResponseEntity<EducationResponseDto> update(EducationUpdateDto educationUpdateDto, Long id, User user, MultipartFile educationFile);

    ResponseEntity<?> removeEducation(Long id, User user);

}
