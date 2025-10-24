package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.ProjectCreateDto;
import uz.tuit.portfolio.dto.request.ProjectUpdateDto;

public interface ProjectService {
    ResponseEntity<?> addProject(User user, @Valid ProjectCreateDto projectCreateDto, Long cvId);

    ResponseEntity<?> updateProject(Long id, User user, @Valid ProjectUpdateDto projectUpdateDto, Long cvId);

    ResponseEntity<?> deleteProject(Long id, User user, Long cvId);

    ResponseEntity<?> updatePortfolioProject(Long id, User user, @Valid ProjectUpdateDto projectUpdateDto);

    ResponseEntity<?> deletePortfolioProject(Long id, User user);

    ResponseEntity<?> addProjectToPortfolio(User user, @Valid ProjectCreateDto projectCreateDto);
}
