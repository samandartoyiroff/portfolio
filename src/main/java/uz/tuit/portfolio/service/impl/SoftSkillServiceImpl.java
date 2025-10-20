package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.SoftSkill;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.SoftSkillCreateDto;
import uz.tuit.portfolio.dto.request.SoftSkillUpdateDto;
import uz.tuit.portfolio.dto.response.SoftSkillResponseDto;
import uz.tuit.portfolio.mapper.SoftSkillMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.SoftSkillRepository;
import uz.tuit.portfolio.repository.UserRepository;
import uz.tuit.portfolio.service.SoftSkillService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SoftSkillServiceImpl implements SoftSkillService {

    private final SoftSkillRepository softSkillRepository;
    private final SoftSkillMapper softSkillMapper;
    private final CVRepository cVRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> search(String query) {

        List<SoftSkill> softSkills = softSkillRepository.search(query);

        List<SoftSkillResponseDto> responseDtos  = softSkills.stream().map(softSkillMapper::toResponseDto).toList();

        return ResponseEntity.ok(responseDtos);
    }

    @Override
    public ResponseEntity<?> create(SoftSkillCreateDto softSkillCreateDto) {

        SoftSkill softSkill = new SoftSkill();
        softSkill.setName(softSkillCreateDto.getName());
        softSkillRepository.save(softSkill);
        return ResponseEntity.ok(softSkillMapper.toResponseDto(softSkill));

    }

    @Override
    public ResponseEntity<?> findById(Long id) {

        SoftSkill softSkill = softSkillRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(softSkillMapper.toResponseDto(softSkill));

    }

    @Override
    public Page<SoftSkillResponseDto> findByPagination(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<SoftSkill> skillPage = softSkillRepository.findAll(pageable);
        return skillPage.map(softSkillMapper::toResponseDto);



    }

    @Override
    @Transactional
    public ResponseEntity<?> updateById(Long id, SoftSkillUpdateDto softSkillUpdateDto) {

        SoftSkill softSkill = softSkillRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        softSkill.setName(softSkillUpdateDto.getName()!=null && !softSkillUpdateDto.getName().isBlank() ?softSkillUpdateDto.getName():softSkill.getName());
        softSkillRepository.save(softSkill);
        return ResponseEntity.ok(softSkillMapper.toResponseDto(softSkill));


    }

    @Override
    @Transactional
    public ResponseEntity<SoftSkillResponseDto> addSoftSkill(User user, Long softSkillId) {

        SoftSkill softSkill = softSkillRepository.findById(softSkillId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        CV cv = user.getCv();
        cv.getSoftSkills().add(softSkill);
        cVRepository.save(cv);

        return ResponseEntity.ok(softSkillMapper.toResponseDto(softSkill));

    }

    @Override
    @Transactional
    public ResponseEntity<?> removeSoftSkill(Long id, User user) {

        Long id1 = user.getCv().getId();

        System.out.println("CV Id: " + id1);
        System.out.println("Soft Skill Id: " + id);

        softSkillRepository.deleteBySoftSkillIdAndCvId(id, id1);
        return ResponseEntity.ok().build();

    }
}
