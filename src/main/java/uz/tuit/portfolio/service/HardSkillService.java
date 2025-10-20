package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.HardSkillCreateDto;
import uz.tuit.portfolio.dto.request.HardSkillUpdateDto;
import uz.tuit.portfolio.dto.response.HardSkillResponseDto;

public interface HardSkillService {
    ResponseEntity<?> search(String query);

    ResponseEntity<?> create(@Valid HardSkillCreateDto hardSkillCreateDto);

    Page<HardSkillResponseDto> findByPagination(Integer page, Integer size);

    ResponseEntity<?> updateById(Long id, @Valid HardSkillUpdateDto hardSkillUpdateDto);

    ResponseEntity<HardSkillResponseDto> findById(Long id);

    ResponseEntity<HardSkillResponseDto> addHardSkill(Long hardSkillId, User user);

    ResponseEntity<?> removeHardSkill(User user, Long id);

}
