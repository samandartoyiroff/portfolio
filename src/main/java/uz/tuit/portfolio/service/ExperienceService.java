package uz.tuit.portfolio.service;

import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.ExperienceCreateDto;
import uz.tuit.portfolio.dto.request.ExperienceUpdateDto;
import uz.tuit.portfolio.dto.response.ExperienceResponseDto;

public interface ExperienceService {

    ResponseEntity<ExperienceResponseDto> addExperience(ExperienceCreateDto experienceCreateDto, User user);

    ResponseEntity<ExperienceResponseDto> updateExperience(Long id, ExperienceUpdateDto experienceUpdateDto);

    ResponseEntity<?> delete(Long id, User user);

}
