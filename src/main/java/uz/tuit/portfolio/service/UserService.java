package uz.tuit.portfolio.service;

import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.UserUpdateDto;
import uz.tuit.portfolio.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    ResponseEntity<List<UserResponseDto>> getAllExceptMe(User user, String query);

    ResponseEntity<List<UserResponseDto>> findAllUsers(User user, String query);

    ResponseEntity<UserResponseDto> updateUser(Long userId, UserUpdateDto userUpdateDto, User user);

}
