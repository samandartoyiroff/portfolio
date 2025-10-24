package uz.tuit.portfolio.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.Image;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.CVCreateDto;
import uz.tuit.portfolio.dto.request.CVUpdateDto;
import uz.tuit.portfolio.dto.response.CVResponseDto;
import uz.tuit.portfolio.mapper.CVMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.service.CVService;
import uz.tuit.portfolio.service.ImageService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService {

    private final CVMapper cVMapper;
    private final CVRepository cVRepository;
    private final ImageService imageService;

    @Override
    @Transactional
    public ResponseEntity<CVResponseDto> createCV(CVCreateDto cvCreateDto, MultipartFile cvImage, User user) {

        CV cv = cVMapper.toEntity(cvCreateDto, cvImage);

        cv.setUser(user);

        cVRepository.save(cv);

        return ResponseEntity.ok().body(cVMapper.toResponseDto(cv, user));


    }

    @Override
    public ResponseEntity<List<CVResponseDto>> getMyAllCv(User user) {

        List<CV> cvs = cVRepository.findByUser(user);
        List<CVResponseDto> cvResponseDtos = cVMapper.toListResponseDto(cvs, user);
        return ResponseEntity.ok(cvResponseDtos);

    }

    @Override
    @Transactional
    public ResponseEntity<?> update(CVUpdateDto cvUpdateDto, User user, MultipartFile profilePhoto, Long cvId) {


        CV cv = cVRepository.findById(cvId).orElseThrow(() -> new RuntimeException("CV Not Found"));

        if (profilePhoto!=null) {
            Image image = imageService.updateImage(profilePhoto, cv.getCvPhoto());
            cv.setCvPhoto(image);
        }

        cv = cVMapper.updateCv(cvUpdateDto, cv);

        cVRepository.save(cv);

        return ResponseEntity.ok(cVMapper.toResponseDto(cv, user));

    }

    @Override
    @Transactional
    public ResponseEntity<?> removeHobby(String hobby, User user, Long cvId) {

        CV cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));

        cVRepository.removeHobby(cv.getId(), hobby);

        return ResponseEntity.ok("Removed hobby");

    }

    @Override
    @Transactional
    public ResponseEntity<?> addHobby(String hobby, User user, Long cvId) {

        CV cv = cVRepository.findByIdAndUserId(cvId, user.getId()).orElseThrow(()-> new EntityNotFoundException("cv not found"));

        if (cv.getHobbies() == null) {
            cv.setHobbies(new ArrayList<>());
        }

        List<String> hobbies = cv.getHobbies();

        hobbies.stream()
                .filter(h -> h.equals(hobby))
                .findAny()
                .ifPresent(h -> {
                    throw new RuntimeException("This hobby already exists");
                });

        hobbies.add(hobby);

        cv.setHobbies(hobbies);

        cVRepository.save(cv);

        return ResponseEntity.ok().body(cVMapper.toResponseDto(cv, user));

    }




}
