package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.SoftSkillCreateDto;
import uz.tuit.portfolio.dto.request.SoftSkillUpdateDto;
import uz.tuit.portfolio.dto.response.SoftSkillResponseDto;

public interface SoftSkillService {
    ResponseEntity<?> search(String query);

    ResponseEntity<?> create(@Valid SoftSkillCreateDto softSkillCreateDto);

    ResponseEntity<?> findById(Long id);

    Page<SoftSkillResponseDto> findByPagination(Integer page, Integer size);

    ResponseEntity<?> updateById(Long id, @Valid SoftSkillUpdateDto softSkillUpdateDto);

    ResponseEntity<SoftSkillResponseDto> addSoftSkill(User user, Long softSkillId, Long cvId);

    ResponseEntity<?> removeSoftSkill(Long id, User user, Long cvId);


    ResponseEntity<SoftSkillResponseDto> addSoftSkillToPortfolio(User user, Long softSkillId);

    ResponseEntity<?> removeSoftSkillFromPortfolio(Long id, User user);
}
