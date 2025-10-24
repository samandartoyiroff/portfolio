package uz.tuit.portfolio.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.Address;
import uz.tuit.portfolio.domain.Image;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.UserUpdateDto;
import uz.tuit.portfolio.dto.request.UserUpdateMeDto;
import uz.tuit.portfolio.dto.response.UserResponseDto;
import uz.tuit.portfolio.mapper.UserMapper;
import uz.tuit.portfolio.repository.UserRepository;
import uz.tuit.portfolio.service.ImageService;
import uz.tuit.portfolio.service.UserService;
import uz.tuit.portfolio.util.RoleUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleUtil roleUtil;
    private final ImageService imageService;

    @Override
    public ResponseEntity<List<UserResponseDto>> getAllExceptMe(User user, String query) {

        List<User> users = userRepository.searchUsersExceptMeAndAdmins(user.getId(), query);

        return  ResponseEntity.ok(userMapper.listDto(users));

    }

    @Override
    public ResponseEntity<List<UserResponseDto>> findAllUsers(User user, String query) {

        List<User> users = userRepository.findAllUsers(user.getId(), query);

        return  ResponseEntity.ok(userMapper.listDto(users));

    }

    @Override
    @Transactional
    public ResponseEntity<UserResponseDto> updateUser(Long userId, UserUpdateDto userUpdateDto, User currentUser) {

        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.getEmail().equals(userUpdateDto.getEmail())) {
            user.setVerified(false);
        }

        if (
                roleUtil.isOnlyAdmin(currentUser)
                        && (roleUtil.isAdmin(user))
        ) throw new RuntimeException("Admin cannot update admin. Only super admin can update admins");

        user = userMapper.updateUser(userUpdateDto, user);

        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toResponseDto(user));

    }

    @Override
    @Transactional
    public ResponseEntity<UserResponseDto> updateMe(User user, UserUpdateMeDto userUpdateDto, MultipartFile profilePhoto) {


        if (profilePhoto!=null){

            if (user.getProfilePhoto()!=null){
                user.setProfilePhoto(imageService.updateImage(profilePhoto, user.getProfilePhoto()));
            }
            else {
                Image image = imageService.uploadImage(profilePhoto);
                user.setProfilePhoto(image);
            }
        }

        if (userUpdateDto.getUsername()!=null && !userUpdateDto.getUsername().isBlank() && !userUpdateDto.getUsername().equals(user.getUsername())) {
            user.setUsername(userUpdateDto.getUsername());
        }

        if (userUpdateDto.getPhoneNumber()!=null && !userUpdateDto.getPhoneNumber().isBlank()) {
            user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        }

        if (userUpdateDto.getFullName()!=null && !userUpdateDto.getFullName().isBlank()) {
            user.setFullName(userUpdateDto.getFullName());
        }

        if (userUpdateDto.getGender()!=null){
            user.setGender(userUpdateDto.getGender());
        }

        if (userUpdateDto.getCvUrl()!=null && !userUpdateDto.getCvUrl().isBlank()) {
            user.setCvUrl(userUpdateDto.getCvUrl());
        }

        if (userUpdateDto.getAddress()!=null){

            if (user.getAddress()==null) {
                user.setAddress(new Address());
            }

            Address address = userUpdateDto.getAddress();

            if (address.getZipCode()!=null &&  !address.getZipCode().isBlank()) {
                user.getAddress().setZipCode(address.getZipCode());
            }

            if (address.getCityTown()!=null &&  !address.getCityTown().isBlank()) {
                user.getAddress().setCityTown(address.getCityTown());
            }
            if (address.getAddress()!=null &&  !address.getAddress().isBlank()) {
                user.getAddress().setAddress(address.getAddress());
            }

        }

        User save = userRepository.save(user);

        return ResponseEntity.ok(userMapper.toResponseDto(save));

    }
}
