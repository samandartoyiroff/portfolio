package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.ProjectCreateDto;
import uz.tuit.portfolio.dto.request.ProjectUpdateDto;

public interface ProjectService {
    ResponseEntity<?> addProject(User user, @Valid ProjectCreateDto projectCreateDto);

    ResponseEntity<?> updateProject(Long id, User user, @Valid ProjectUpdateDto projectUpdateDto);

    ResponseEntity<?> deleteProject(Long id, User user);

}
