package uz.tuit.portfolio.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.OTPCode;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.*;
import uz.tuit.portfolio.dto.response.OTPDTO;
import uz.tuit.portfolio.dto.response.RegisterResponseDto;
import uz.tuit.portfolio.dto.response.UserResponseDto;
import uz.tuit.portfolio.mapper.OTPMapper;
import uz.tuit.portfolio.mapper.UserMapper;
import uz.tuit.portfolio.model.UserStatus;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.OTPCodeRepository;
import uz.tuit.portfolio.repository.UserRepository;
import uz.tuit.portfolio.service.AuthService;
import uz.tuit.portfolio.service.EmailService;
import uz.tuit.portfolio.util.CookieUtil;
import uz.tuit.portfolio.util.PermissionUtil;
import uz.tuit.portfolio.util.RoleUtil;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtil cookieUtil;
    private final UserMapper userMapper;
    private final OTPCodeRepository oTPCodeRepository;
    private final EmailService emailService;
    private final OTPMapper otpMapper;
    private final RoleUtil roleUtil;
    private final CVRepository cVRepository;
    private final PermissionUtil permissionUtil;


    @Override
    public ResponseEntity<?> loginAdmin(LoginDto loginDto, HttpServletResponse response, HttpServletRequest request) {

        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        if (!roleUtil.isAdmin(user)) throw new IllegalArgumentException("Siz admin emassiz");

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) throw new IllegalArgumentException("Wrong password");

        ResponseCookie jwtCookie = cookieUtil.generateJwtCookie(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userMapper.toResponseDto(user));

    }

    @Override
    @Transactional
    public ResponseEntity<?> sendOtpCode(String email) {

        Random random = new Random();

        OTPCode otpCode = new OTPCode();
        String code = random.nextInt(1000, 9999) + "";

        emailService.sendVerificationEmail(email, code);

        otpCode.setCode(code);
        otpCode.setEmail(email);

        OTPCode save = oTPCodeRepository.save(otpCode);

        OTPDTO dto = otpMapper.toDto(save);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setVerified(false);
        userRepository.save(user);

        return ResponseEntity.ok().body(dto);


    }

    @Override
    @Transactional
    public ResponseEntity<?> register(RegisterDto registerDto) {

        CV cv = new CV();
        cv.setFullName(registerDto.getFullName());
        cv.setEmail(registerDto.getEmail());
        cVRepository.save(cv);

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setVerified(false);
        user.setRoles(roleUtil.rolesForUser());
        user.setPermissions(permissionUtil.permissionForUser());
        user.setFullName(registerDto.getFullName());
        user.setGender(registerDto.getGender());
        user.setCv(cv);
        userRepository.save(user);

        Random random = new Random();

        OTPCode otpCode = new OTPCode();
        String code = random.nextInt(1000, 9999) + "";

        emailService.sendVerificationEmail(user.getEmail(), code);

        otpCode.setCode(code);
        otpCode.setEmail(user.getEmail());

        OTPCode save = oTPCodeRepository.save(otpCode);
        OTPDTO otpdto = otpMapper.toDto(save);

        RegisterResponseDto registerResponseDto  = new RegisterResponseDto();


        UserResponseDto userResponseDto = userMapper.toResponseDto(user);
        registerResponseDto.setOtp(otpdto);
        registerResponseDto.setUser(userResponseDto);

        return ResponseEntity.ok().body(registerResponseDto);

    }

    @Override
    @Transactional
    public ResponseEntity<?> resetPassword(PasswordRecoverDto passwordChangeDto) {

        User user = userRepository.findByEmail(passwordChangeDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.isVerified()) {
            user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.ok(userMapper.toResponseDto(user));
        }

        throw new IllegalArgumentException("User not verified");

    }

    @Override
    @Transactional
    public ResponseEntity<?> forgetPassword(String email) {


        Random random = new Random();

        OTPCode otpCode = new OTPCode();

        String code = random.nextInt(1000, 9999) + "";

        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));

        emailService.sendVerificationEmail(email, code);

        otpCode.setCode(code);

        otpCode.setEmail(email);

        oTPCodeRepository.save(otpCode);

        user.setVerified(false);

        OTPDTO otpdto = otpMapper.toDto(otpCode);

        userRepository.save(user);

        return ResponseEntity.ok().body(otpdto);

    }

    @Override
    public UserResponseDto getProfile(User user) {
        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public ResponseEntity<?> changePassword(PasswordChangeDto passwordChangeDto, User user) {

        if(passwordEncoder.matches(passwordChangeDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
            return ResponseEntity.ok().body(userMapper.toResponseDto(user));
        }
        throw new IllegalArgumentException("Old Password Doesn't Match");



    }

    @Override
    public ResponseEntity<?> verify(VerifyDto verifyDto) {

        OTPCode otpCode = oTPCodeRepository.findById(verifyDto.getId())
                .orElseThrow(() -> new RuntimeException("Otp not found"));
        if (!otpCode.getEmail().equals(verifyDto.getEmail())) throw new RuntimeException("Wrong email");
        if (!otpCode.getCode().equals(verifyDto.getCode())) throw new RuntimeException("Wrong code");
        if (otpCode.getExpirationDate().isBefore(LocalDateTime.now())) throw new RuntimeException("Otp expired");
        User user = userRepository.findByEmail(verifyDto.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        user.setVerified(true);
        user.setStatus(UserStatus.ACTIVE);
        User save = userRepository.save(user);

        return ResponseEntity.ok().body(userMapper.toResponseDto(save));

    }


    @Override
    public ResponseEntity<?> loginUser(LoginDto loginDto, HttpServletResponse response, HttpServletRequest request) {


        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username not found"));

        if (!user.isVerified()) throw new IllegalArgumentException("Username not verified");

        if (!user.getStatus().equals(UserStatus.ACTIVE)) throw new IllegalArgumentException("Username not active");

        if (!roleUtil.isUser(user)) throw new IllegalArgumentException("Siz user emassiz");

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) throw new IllegalArgumentException("Wrong password");

        ResponseCookie jwtCookie = cookieUtil.generateJwtCookie(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userMapper.toResponseDto(user));

    }


}
