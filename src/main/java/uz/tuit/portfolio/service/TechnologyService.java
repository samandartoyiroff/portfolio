package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.TechnologyCreateDto;
import uz.tuit.portfolio.dto.request.TechnologyUpdateDto;
import uz.tuit.portfolio.dto.response.TechnologyResponseDto;

import java.util.List;

public interface TechnologyService {

    ResponseEntity<?> create(@Valid TechnologyCreateDto technologyCreateDto, MultipartFile file);

    TechnologyResponseDto findById(Long id);

    Page<TechnologyResponseDto> findByPagination(Integer page, Integer size);

    List<TechnologyResponseDto> search(String query);

    ResponseEntity<?> updateById(Long id, MultipartFile file, TechnologyUpdateDto technologyUpdateDto);

    ResponseEntity<TechnologyResponseDto> addTechnologyInPortfolio(Long technologyId, User user);

    ResponseEntity<?> removeTechnologyFromPortfolio(Long id, User user);
}
