package uz.tuit.portfolio.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.LoginDto;
import uz.tuit.portfolio.dto.request.PasswordChangeDto;
import uz.tuit.portfolio.dto.request.PasswordRecoverDto;
import uz.tuit.portfolio.dto.request.RegisterDto;
import uz.tuit.portfolio.dto.request.VerifyDto;
import uz.tuit.portfolio.model.Permission;
import uz.tuit.portfolio.repository.RoleRepository;
import uz.tuit.portfolio.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final RoleRepository roleRepository;

    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(
            @RequestBody @Valid LoginDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response
    )  {
        return authService.loginAdmin(loginDto, response, request);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(
            @RequestBody @Valid LoginDto loginDto,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        return authService.loginUser(loginDto, response, request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterDto registerDto
    ){
        return authService.register(registerDto);
    }

    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(
            @RequestParam(name = "email") String email
    ){
        return authService.sendOtpCode(email);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(
            @RequestBody @Valid VerifyDto verifyDto
    ){
        return authService.verify(verifyDto);
    }

    @PostMapping("/change-password-with-email")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid PasswordRecoverDto passwordChangeDto
    ){
        return authService.resetPassword(passwordChangeDto);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(
            @RequestParam(name = "email") String email
    ){
        return authService.forgetPassword(email);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid PasswordChangeDto passwordChangeDto,
            @AuthenticationPrincipal User user
    ){
        return authService.changePassword(passwordChangeDto, user);
    }

    @GetMapping("/permissions")
    public ResponseEntity<?> getPermissions(){
        return ResponseEntity.ok(Permission.values());
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(){
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(authService.getProfile(user));
    }

}
