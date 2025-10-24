package uz.tuit.portfolio.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.annotations.ValidEmail;
import uz.tuit.portfolio.annotations.ValidPassword;
import uz.tuit.portfolio.annotations.ValidPhone;
import uz.tuit.portfolio.annotations.ValidUsername;
import uz.tuit.portfolio.domain.Address;
import uz.tuit.portfolio.model.Gender;
import uz.tuit.portfolio.model.Permission;
import uz.tuit.portfolio.model.UserStatus;

import java.util.Set;

@Setter
@Getter
@ToString
public class UserUpdateMeDto {

    @ValidUsername
    private String username;

    @ValidPhone
    private String phoneNumber;

    private String fullName;

    private Gender gender;

    private String cvUrl;

    private Address address;

}
