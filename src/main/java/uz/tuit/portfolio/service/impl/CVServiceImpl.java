package uz.tuit.portfolio.service.impl;

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
import uz.tuit.portfolio.repository.UserRepository;
import uz.tuit.portfolio.service.CVService;
import uz.tuit.portfolio.service.ImageService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService {

    private final CVMapper cVMapper;
    private final UserRepository userRepository;
    private final CVRepository cVRepository;
    private final ImageService imageService;

    @Override
    @Transactional
    public ResponseEntity<CVResponseDto> createCV(CVCreateDto cvCreateDto, MultipartFile cvImage, User user) {

        CV cv = cVMapper.toEntity(cvCreateDto, cvImage);

        user.setCv(cv);

        userRepository.save(user);

        return ResponseEntity.ok().body(cVMapper.toResponseDto(cv, user));


    }

    @Override
    public ResponseEntity<CVResponseDto> getMyCv(User user) {

        CV cv = user.getCv();
        CVResponseDto cvResponseDto = cVMapper.toResponseDto(cv, user);
        return ResponseEntity.ok(cvResponseDto);

    }

    @Override
    @Transactional
    public ResponseEntity<?> update(CVUpdateDto cvUpdateDto, User user, MultipartFile profilePhoto) {


        CV cv = user.getCv();

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
    public ResponseEntity<?> removeHobby(String hobby, User user) {

        CV cv = user.getCv();

        List<String> hobbies = cv.getHobbies();

        List<String> newHobbies = hobbies.stream().filter(h->!h.equals(hobby)).toList();

        cv.setHobbies(newHobbies);

        cVRepository.save(cv);

        return ResponseEntity.ok().body(cVMapper.toResponseDto(cv, user));

    }

    @Override
    @Transactional
    public ResponseEntity<?> addHobby(String hobby, User user) {

        CV cv = user.getCv();

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
