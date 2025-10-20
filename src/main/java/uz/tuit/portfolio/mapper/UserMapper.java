package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Role;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.UserUpdateDto;
import uz.tuit.portfolio.dto.response.UserResponseDto;
import uz.tuit.portfolio.model.Permission;
import uz.tuit.portfolio.repository.UserRepository;
import uz.tuit.portfolio.util.EmailValidator;
import uz.tuit.portfolio.util.RoleUtil;
import uz.tuit.portfolio.util.Validator;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final EmailValidator emailValidator;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleUtil roleUtil;

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setCreatedDate(user.getCreatedAt());
        userResponseDto.setUpdatedDate(user.getUpdatedAt());
        userResponseDto.setCreatedBy(user.getCreatedBy()!=null ? user.getCreatedBy().getId() : null);
        userResponseDto.setUpdatedBy(user.getUpdatedBy()!=null ? user.getUpdatedBy().getId() : null);
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setPhoneNumber(String.valueOf(user.getPhoneNumber()));
        userResponseDto.setStatus(user.getStatus());
        userResponseDto.setIsverified(user.isVerified());
        userResponseDto.setRoles(

                user.getRoles().stream().map(Role::getDescription).collect(Collectors.toSet())

        );

        userResponseDto.setPermissions(
                user.getPermissions().stream().map(Permission::toString).collect(Collectors.toSet())
        );

        userResponseDto.setImageUrl(
                user.getProfilePhoto()!=null ? user.getProfilePhoto().getPath() : null
        );

        if (user.getCv() != null) {
            userResponseDto.setCvId(user.getCv().getId());
        }

        return userResponseDto;
    }

    public List<UserResponseDto> listDto(List<User> users) {

        return users.stream().map(this::toResponseDto).collect(Collectors.toList());

    }

    public User updateUser(UserUpdateDto userUpdateDto, User user) {

        if (userUpdateDto.getFullName() != null && !userUpdateDto.getFullName().isBlank()) {
            user.setFullName(userUpdateDto.getFullName());
        }
        if (userUpdateDto.getPhoneNumber() != null && !userUpdateDto.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        }
        if (userUpdateDto.getEmail() != null  && !userUpdateDto.getEmail().isBlank()) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPassword() != null  && !userUpdateDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }
        if (userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().isBlank()) {
            user.setUsername(userUpdateDto.getUsername());
        }
        if (userUpdateDto.getStatus() != null) {
            user.setStatus(userUpdateDto.getStatus());
        }

        if (userUpdateDto.getPermissions() != null &&  !userUpdateDto.getPermissions().isEmpty()) {
            user.setPermissions(userUpdateDto.getPermissions());
        }

        if (userUpdateDto.getRolesId() != null && !userUpdateDto.getRolesId().isEmpty()) {
            user.setRoles(roleUtil.customRoles(userUpdateDto.getRolesId()));
        }

        return user;


    }
}
