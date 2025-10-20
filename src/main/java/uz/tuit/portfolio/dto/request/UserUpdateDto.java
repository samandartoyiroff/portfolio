package uz.tuit.portfolio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.annotations.ValidEmail;
import uz.tuit.portfolio.annotations.ValidPassword;
import uz.tuit.portfolio.annotations.ValidPhone;
import uz.tuit.portfolio.annotations.ValidUsername;
import uz.tuit.portfolio.model.Permission;
import uz.tuit.portfolio.model.UserStatus;

import java.util.Set;

@Setter
@Getter
@ToString
public class UserUpdateDto {

    @ValidUsername
    private String username;

    @ValidPassword
    private String password;

    @ValidEmail
    private String email;

    @ValidPhone
    private String phoneNumber;

    private Set<Long> rolesId;

    private Set<Permission> permissions;

    private String fullName;

    private UserStatus status;

}
