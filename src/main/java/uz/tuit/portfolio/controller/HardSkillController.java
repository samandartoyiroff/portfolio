package uz.tuit.portfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.tuit.portfolio.dto.request.HardSkillCreateDto;
import uz.tuit.portfolio.dto.request.HardSkillUpdateDto;
import uz.tuit.portfolio.dto.response.HardSkillResponseDto;
import uz.tuit.portfolio.service.HardSkillService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hard-skill")
public class HardSkillController {

    private final HardSkillService  hardSkillService;

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(name = "query", required = false) String query
    ){
        return hardSkillService.search(query);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody @Valid HardSkillCreateDto hardSkillCreateDto
            ){
        return hardSkillService.create(hardSkillCreateDto);
    }

    @GetMapping("/findAll/pagination")
    public Page<HardSkillResponseDto> findAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "30") Integer size
    ){
        return hardSkillService.findByPagination(page, size);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(

            @RequestBody @Valid HardSkillUpdateDto hardSkillUpdateDto,
            @PathVariable Long id

    ){
        return hardSkillService.updateById(id,hardSkillUpdateDto);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<HardSkillResponseDto> findById(
            @PathVariable Long id
    ){
        return hardSkillService.findById(id);
    }

}
