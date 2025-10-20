package uz.tuit.portfolio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.Gender;

@Setter
@Getter
@ToString
public class UserSubscriptionStatisticsDto {

    private Long id;

    private Long userId;

    private Long subscriptionId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String profilePhotoUrl;

    private Long cvId;

    private Boolean isSubscribed;

    private String cvUrl;

    private Gender gender;

}
