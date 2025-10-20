package uz.tuit.portfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.tuit.portfolio.dto.request.SoftSkillCreateDto;
import uz.tuit.portfolio.dto.request.SoftSkillUpdateDto;
import uz.tuit.portfolio.dto.response.SoftSkillResponseDto;
import uz.tuit.portfolio.service.SoftSkillService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/soft-skill")
@RestController
public class SoftSkillController {

    private final SoftSkillService softSkillService;

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(name = "query", required = false) String query
    ) {
        return softSkillService.search(query);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody @Valid SoftSkillCreateDto softSkillCreateDto
    ){
        return softSkillService.create(softSkillCreateDto);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(
            @PathVariable(name = "id") Long id
    ){
        return softSkillService.findById(id);
    }

    @GetMapping("/findAll/pagination")
    public Page<SoftSkillResponseDto> findAll(

            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size

    ){
        return softSkillService.findByPagination(page, size);
    }

    @PostMapping("/updateById/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid SoftSkillUpdateDto softSkillUpdateDto
    ){
        return softSkillService.updateById(id, softSkillUpdateDto);
    }
}
