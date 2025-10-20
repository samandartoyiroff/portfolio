package uz.tuit.portfolio.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.Currency;
import uz.tuit.portfolio.model.DurationUnit;
import uz.tuit.portfolio.model.SubscriptionStatus;

@Setter
@Getter
@ToString
public class SubscriptionResponseDto extends AuditableResponseDto {

    private Long id;

    private String name;

    private Double realPrice;

    private Double discountPrice;

    private Double discountPercentage;

    private Integer freeCvCount;

    private boolean isDiscount;

    private Integer durationValue;

    private Currency currency;

    private DurationUnit durationUnit;

    private SubscriptionStatus status;

}
