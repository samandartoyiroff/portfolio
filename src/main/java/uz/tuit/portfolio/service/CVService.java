package uz.tuit.portfolio.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.CVCreateDto;
import uz.tuit.portfolio.dto.request.CVUpdateDto;
import uz.tuit.portfolio.dto.response.CVResponseDto;

import java.util.List;

public interface CVService {
    ResponseEntity<CVResponseDto> createCV(CVCreateDto cvCreateDto, MultipartFile cvImage, User user);

    ResponseEntity<List<CVResponseDto>> getMyAllCv(User user);

    ResponseEntity<?> update(CVUpdateDto cvUpdateDto, User user, MultipartFile profilePhoto, Long cvId);

    ResponseEntity<?> addHobby(String hobby, User user, Long cvId);

    ResponseEntity<?> removeHobby(String hobby, User user, Long cvId);

}
