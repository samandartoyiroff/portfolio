package uz.tuit.portfolio.dto.response;

import lombok.*;
import uz.tuit.portfolio.model.Currency;
import uz.tuit.portfolio.model.DurationUnit;
import uz.tuit.portfolio.model.UserSubscriptionStatus;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscriptionResponseDto extends AuditableResponseDto {

    private Long id;

    private String name;

    private Long subscriptionId;

    private Long userId;

    private Double price;

    private Double discountPrice;

    private Double discountPercentage;

    private Integer freeCvCount;

    private Integer remainingCvCount;

    private Currency currency;

    private boolean isDiscount;

    private Integer durationValue;

    private DurationUnit durationUnit;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer remainingDays;

    private UserSubscriptionStatus subscriptionStatus;

}
