package uz.tuit.portfolio.dto.request;

import lombok.*;
import uz.tuit.portfolio.domain.Address;
import uz.tuit.portfolio.domain.ContactInfo;
import uz.tuit.portfolio.model.Gender;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PortfolioUpdateDto {

    private String fullName;

    private String email;

    private String phoneNumber;

    private Address address;

    private String aboutMe;

    private ContactInfo contactInfo;

    private String driverLicense;

    private Gender gender;

    private String template;

}
