package uz.tuit.portfolio.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.HardSkill;
import uz.tuit.portfolio.domain.Portfolio;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.HardSkillCreateDto;
import uz.tuit.portfolio.dto.request.HardSkillUpdateDto;
import uz.tuit.portfolio.dto.response.HardSkillResponseDto;
import uz.tuit.portfolio.mapper.HardSkillMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.HardSkillRepository;
import uz.tuit.portfolio.repository.PortfolioRepository;
import uz.tuit.portfolio.service.HardSkillService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HardSkillServiceImpl implements HardSkillService {

    private final HardSkillRepository hardSkillRepository;
    private final CVRepository cvRepository;
    private final HardSkillMapper hardSkillMapper;
    private final CVRepository cVRepository;
    private final PortfolioRepository portfolioRepository;

    @Override
    public ResponseEntity<?> search(String query) {

        List<HardSkill> hardSkills = hardSkillRepository.search(query);
        List<HardSkillResponseDto> collect = hardSkills.stream().map(hardSkillMapper::toResponseDto).collect(Collectors.toList());
        return ResponseEntity.ok(collect);



    }

    @Override
    public ResponseEntity<?> create(HardSkillCreateDto hardSkillCreateDto) {


        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillCreateDto.getName());
        hardSkillRepository.save(hardSkill);
        return ResponseEntity.ok(hardSkillMapper.toResponseDto(hardSkill));

    }

    @Override
    public Page<HardSkillResponseDto> findByPagination(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<HardSkill> hardSkills = hardSkillRepository.findAll(pageable);
        return hardSkills.map(hardSkillMapper::toResponseDto);

    }

    @Override
    @Transactional
    public ResponseEntity<?> updateById(Long id, HardSkillUpdateDto hardSkillUpdateDto) {

        HardSkill hardSkill = hardSkillRepository
                .findById(id).orElseThrow(() -> new RuntimeException("HardSkill not found"));

        hardSkill.setName(hardSkillUpdateDto.getName()!=null && !hardSkillUpdateDto.getName().isBlank()  ?hardSkillUpdateDto.getName():hardSkill.getName());
        HardSkill saved = hardSkillRepository.save(hardSkill);
        return ResponseEntity.ok(hardSkillMapper.toResponseDto(saved));


    }

    @Override
    public ResponseEntity<HardSkillResponseDto> findById(Long id) {

        HardSkill hardSkill = hardSkillRepository
                .findById(id).orElseThrow(() -> new RuntimeException("HardSkill not found"));
        return ResponseEntity.ok(hardSkillMapper.toResponseDto(hardSkill));

    }

    @Override
    @Transactional
    public ResponseEntity<HardSkillResponseDto> addHardSkill(HardSkillCreateDto hardSkillCreateDto, User user, Long cvId) {

        HardSkill hardSkill = hardSkillMapper.toEntity(hardSkillCreateDto);
        hardSkillRepository.save(hardSkill);

        CV cv;

        if (user != null) {
            cv = cvRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }
        else {
            cv = cvRepository.findById(cvId).orElseThrow(()-> new EntityNotFoundException("cv not found"));
        }
        List<HardSkill> hardSkills = cv.getHardSkills();
        if (hardSkills==null) {
            hardSkills = new ArrayList<>();
        }
        hardSkills.add(hardSkill);
        cv.setHardSkills(hardSkills);
        cVRepository.save(cv);
        return ResponseEntity.ok(hardSkillMapper.toResponseDto(hardSkill));



    }

    @Override
    @Transactional
    public ResponseEntity<?> removeHardSkill(User user, Long id, Long cvId) {

        CV cv;

        if (user != null) {
            cv = cvRepository.findByIdAndUserId(cvId, user.getId())
                    .orElseThrow(() -> new EntityNotFoundException("cv not found"));
        } else {
            cv = cvRepository.findById(cvId)
                    .orElseThrow(() -> new EntityNotFoundException("cv not found"));
        }

        // 1. CV dan ham unlink qilish
        cv.getHardSkills().removeIf(h -> h.getId().equals(id));

        // 2. Join jadvaldan o‘chirish
        hardSkillRepository.removeFromUserHardSkillTable(id, cv.getId());

        // 3. HardSkill ni bazadan o‘chirish
        hardSkillRepository.deleteById(id);

        return ResponseEntity.ok("HardSkill has been removed");
    }


    @Override
    public ResponseEntity<HardSkillResponseDto> addHardSkillToPortfolio(Long hardSkillId, User user) {

        HardSkill hardSkill = hardSkillRepository.findById(hardSkillId)
                .orElseThrow(() -> new RuntimeException("HardSkill not found"));

        Portfolio portfolio = user.getPortfolio();

        List<HardSkill> hardSkills = portfolio.getHardSkills();
        if (hardSkills==null) {
            hardSkills = new ArrayList<>();
        }
        hardSkills.add(hardSkill);
        portfolio.setHardSkills(hardSkills);
        portfolioRepository.save(portfolio);
        return ResponseEntity.ok(hardSkillMapper.toResponseDto(hardSkill));

    }

    @Override
    @Transactional
    public ResponseEntity<?> removeHardSkillFromPortfolio(User user, Long id) {

        Portfolio portfolio = user.getPortfolio();

        hardSkillRepository.removeFromUserHardSkillTableInPortfolio(id, portfolio.getId());

        return ResponseEntity.ok("HardSkill has been removed");

    }
}
