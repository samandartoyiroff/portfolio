package uz.tuit.portfolio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.Currency;
import uz.tuit.portfolio.model.DurationUnit;
import uz.tuit.portfolio.model.SubscriptionStatus;

import java.util.List;

@Setter
@Getter
@ToString
public class SubscriptionUpdateDto {

    private String name;

    private Double realPrice;

    private List<String> freeServices;

    @NotNull
    private Double discountPrice;

    private Integer freeCvCount;

    private Boolean isDiscount;

    private Integer durationValue;

    private Currency currency;

    private DurationUnit durationUnit;

    private SubscriptionStatus status;
}
