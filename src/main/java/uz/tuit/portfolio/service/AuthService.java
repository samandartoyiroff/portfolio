package uz.tuit.portfolio.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.LoginDto;
import uz.tuit.portfolio.dto.request.PasswordChangeDto;
import uz.tuit.portfolio.dto.request.PasswordRecoverDto;
import uz.tuit.portfolio.dto.request.RegisterDto;
import uz.tuit.portfolio.dto.request.VerifyDto;
import uz.tuit.portfolio.dto.response.UserResponseDto;

public interface AuthService {
    ResponseEntity<?> loginAdmin(@Valid LoginDto loginDto, HttpServletResponse response, HttpServletRequest request);

    ResponseEntity<?> loginUser(@Valid LoginDto loginDto, HttpServletResponse response, HttpServletRequest request);

    ResponseEntity<?> verify(VerifyDto verifyDto);

    ResponseEntity<?> sendOtpCode(String email);

    ResponseEntity<?> register(@Valid RegisterDto registerDto);

    ResponseEntity<?> resetPassword(@Valid PasswordRecoverDto passwordChangeDto);

    ResponseEntity<?> forgetPassword(String email);

    UserResponseDto getProfile(User user);

    ResponseEntity<?> changePassword(@Valid PasswordChangeDto passwordChangeDto, User user);

}
