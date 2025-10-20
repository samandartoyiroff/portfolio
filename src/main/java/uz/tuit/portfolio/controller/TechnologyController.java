package uz.tuit.portfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.dto.request.TechnologyCreateDto;
import uz.tuit.portfolio.dto.request.TechnologyUpdateDto;
import uz.tuit.portfolio.dto.response.TechnologyResponseDto;
import uz.tuit.portfolio.service.TechnologyService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/technology")
@RequiredArgsConstructor
public class TechnologyController {

    private final TechnologyService technologyService;

    @PostMapping("/create")
    public ResponseEntity<?> addTechnology(
            @RequestPart @Valid TechnologyCreateDto technologyCreateDto,
            @RequestPart MultipartFile file
            ){
        return technologyService.create(technologyCreateDto, file);
    }

    @GetMapping("/findById/{id}")
    public TechnologyResponseDto findTechnology(@PathVariable(name = "id") Long id){
        return technologyService.findById(id);
    }

    @GetMapping("/findAll/pagination")
    public Page<TechnologyResponseDto> getTechnologies(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size
    ){
        return technologyService.findByPagination(page, size);
    }

    @GetMapping("/search")
    public List<TechnologyResponseDto> getTechnologies(
            @RequestParam(name = "query") String query
    ){
        return technologyService.search(query);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable(name = "id") Long id,
            @RequestPart(name = "technologyLogo", required = false) MultipartFile file,
            @RequestPart(name = "technologyUpdateDto", required = false) TechnologyUpdateDto technologyUpdateDto
    ){
        return technologyService.updateById(id, file, technologyUpdateDto);
    }

}
