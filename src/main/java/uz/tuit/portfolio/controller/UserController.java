package uz.tuit.portfolio.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.UserUpdateDto;
import uz.tuit.portfolio.dto.request.UserUpdateMeDto;
import uz.tuit.portfolio.dto.response.UserResponseDto;
import uz.tuit.portfolio.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService  userService;

    @GetMapping("/get-all-except-me")
    public ResponseEntity<List<UserResponseDto>> getAllExceptMe(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "query", required = false) String query
    ){
        return userService.getAllExceptMe(user, query);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "query", required = false) String query

    ){
        return userService.findAllUsers(user, query);
    }

    // admin
    @PostMapping("/update/{userId}")
    public ResponseEntity<UserResponseDto> updateUser(

            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserUpdateDto userUpdateDto,
            @PathVariable Long userId

    ){
        return userService.updateUser(userId, userUpdateDto, user);
    }

    @PostMapping("/updateMe")
    public ResponseEntity<UserResponseDto> updateUser(
            @AuthenticationPrincipal User user,
            @RequestPart @Valid UserUpdateMeDto userUpdateDto,
            @RequestPart(required = false) MultipartFile profilePhoto
    ){
        return userService.updateMe(user, userUpdateDto, profilePhoto);
    }



}
