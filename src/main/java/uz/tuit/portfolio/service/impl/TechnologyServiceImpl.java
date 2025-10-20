package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.Image;
import uz.tuit.portfolio.domain.Technology;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.TechnologyCreateDto;
import uz.tuit.portfolio.dto.request.TechnologyUpdateDto;
import uz.tuit.portfolio.dto.response.TechnologyResponseDto;
import uz.tuit.portfolio.mapper.TechnologyMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.TechnologyRepository;
import uz.tuit.portfolio.service.ImageService;
import uz.tuit.portfolio.service.TechnologyService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnologyServiceImpl implements TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final TechnologyMapper technologyMapper;
    private final ImageService imageService;
    private final CVRepository cVRepository;

    @Transactional
    @Override
    public ResponseEntity<?> create(TechnologyCreateDto technologyCreateDto, MultipartFile file) {

        Technology technology = new Technology();

        Image image = imageService.uploadImage(file);

        technology.setLogo(image);

        technology.setName(technologyCreateDto.getName());

        technologyRepository.save(technology);

        return ResponseEntity.ok().body(technologyMapper.toResponseDto(technology));


    }

    @Override
    public TechnologyResponseDto findById(Long id) {

        Technology technology = technologyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Technology not found"));
        return technologyMapper.toResponseDto(technology);

    }

    @Override
    public Page<TechnologyResponseDto> findByPagination(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Technology> technologies = technologyRepository.findAll(pageRequest);
        return technologies.map(technologyMapper::toResponseDto)
                ;
    }

    @Override
    public List<TechnologyResponseDto> search(String query) {

        List<Technology> technologies = technologyRepository.search(query);
        return technologies.stream().map(technologyMapper::toResponseDto).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public ResponseEntity<?> updateById(Long id, MultipartFile file, TechnologyUpdateDto technologyUpdateDto) {

        Technology technology = technologyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Technology not found"));

        if (file!=null && !file.isEmpty()) {
            Image image = imageService.updateImage(file, technology.getLogo());
            technology.setLogo(image);
        }

        if (technologyUpdateDto!=null){
            technology.setName(
                    technologyUpdateDto.getName()!=null && !technologyUpdateDto.getName().isBlank()
                            ?technologyUpdateDto.getName()
                            :technology.getName());
        }

        technologyRepository.save(technology);
        return ResponseEntity.ok().body(technologyMapper.toResponseDto(technology));

    }

    @Override
    @Transactional
    public ResponseEntity<TechnologyResponseDto> addTechnology(Long technologyId, User user) {

        CV cv = user.getCv();

        List<Technology> technologies = cv.getTechnologies();

        Technology technology = technologyRepository.findById(technologyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Technology not found"));

        technologies.add(technology);

        cv.setTechnologies(technologies);

        cVRepository.save(cv);

        return ResponseEntity.ok().body(technologyMapper.toResponseDto(technology));

    }

    @Override
    @Transactional
    public ResponseEntity<?> removeTechnology(Long id, User user) {

        technologyRepository.deleteByCvIdAndTechnologyId(user.getCv().getId(), id);
        return ResponseEntity.ok().body("Technology has been removed");

    }
}
