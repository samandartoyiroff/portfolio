package uz.tuit.portfolio.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
import uz.tuit.portfolio.domain.Portfolio;
import uz.tuit.portfolio.domain.SoftSkill;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.SoftSkillCreateDto;
import uz.tuit.portfolio.dto.request.SoftSkillUpdateDto;
import uz.tuit.portfolio.dto.response.SoftSkillResponseDto;
import uz.tuit.portfolio.mapper.SoftSkillMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.PortfolioRepository;
import uz.tuit.portfolio.repository.SoftSkillRepository;
import uz.tuit.portfolio.repository.UserRepository;
import uz.tuit.portfolio.service.SoftSkillService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SoftSkillServiceImpl implements SoftSkillService {

    private final SoftSkillRepository softSkillRepository;
    private final SoftSkillMapper softSkillMapper;
    private final CVRepository cVRepository;
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    public ResponseEntity<?> search(String query) {

        List<SoftSkill> softSkills = softSkillRepository.search(query);

        List<SoftSkillResponseDto> responseDtos  = softSkills.stream().map(softSkillMapper::toResponseDto).toList();

        return ResponseEntity.ok(responseDtos);
    }

    @Override
    @Transactional
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
    public ResponseEntity<SoftSkillResponseDto> addSoftSkill(User user, SoftSkillCreateDto softSkillCreateDto, Long cvId) {

        SoftSkill softSkill = softSkillMapper.toEntity(softSkillCreateDto);

        softSkillRepository.save(softSkill);


        CV cv;

        if (user != null) {
            cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }
        else {
            cv = cVRepository.findById(cvId).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }

        cv.getSoftSkills().add(softSkill);
        cVRepository.save(cv);

        return ResponseEntity.ok(softSkillMapper.toResponseDto(softSkill));

    }

    @Override
    @Transactional
    public ResponseEntity<?> removeSoftSkill(Long softSkillId, User user, Long cvId) {

        CV cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));

        softSkillRepository.deleteBySoftSkillIdAndCvId(softSkillId, cv.getId());

        return ResponseEntity.ok().build();

    }

    @Override
    @Transactional
    public ResponseEntity<SoftSkillResponseDto> addSoftSkillToPortfolio(User user, @Valid SoftSkillCreateDto softSkillCreateDto) {
        SoftSkill softSkill =softSkillMapper.toEntity(softSkillCreateDto);
        softSkillRepository.save(softSkill);

        Portfolio portfolio = user.getPortfolio();

        portfolio.getSoftSkills().add(softSkill);
        portfolioRepository.save(portfolio);

        return ResponseEntity.ok(softSkillMapper.toResponseDto(softSkill));
    }

    @Override
    @Transactional
    public ResponseEntity<?> removeSoftSkillFromPortfolio(Long id, User user) {

        Portfolio portfolio = user.getPortfolio();

        softSkillRepository.deleteBySoftSkillIdAndPortfolioId(id, portfolio.getId());

        return ResponseEntity.ok().build();
    }
}
