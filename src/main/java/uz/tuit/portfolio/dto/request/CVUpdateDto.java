package uz.tuit.portfolio.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.*;
import uz.tuit.portfolio.domain.Address;
import uz.tuit.portfolio.domain.ContactInfo;
import uz.tuit.portfolio.model.Gender;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CVUpdateDto {

    private String fullName;

    private String email;

    private String phoneNumber;

    private Address address;

    private String aboutMe;

    private ContactInfo contactInfo;

    private String driverLicense;

    private Gender gender;

    private String template;

    private Date dateOfBirth;


}
