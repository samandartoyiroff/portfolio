package uz.tuit.portfolio.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.CVCreateDto;
import uz.tuit.portfolio.dto.request.CVUpdateDto;
import uz.tuit.portfolio.dto.response.CVResponseDto;

public interface CVService {
    ResponseEntity<CVResponseDto> createCV(CVCreateDto cvCreateDto, MultipartFile cvImage, User user);

    ResponseEntity<CVResponseDto> getMyCv(User user);

    ResponseEntity<?> update(CVUpdateDto cvUpdateDto, User user, MultipartFile profilePhoto);

    ResponseEntity<?> addHobby(String hobby, User user);

    ResponseEntity<?> removeHobby(String hobby, User user);

}
