package uz.tuit.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.Currency;
import uz.tuit.portfolio.model.DurationUnit;

import java.util.List;

@Setter
@Getter
@ToString
public class SubscriptionCreateDto {

    @NotBlank
    private String name;

    @NotNull
    private Double realPrice;

    private List<String> freeServices;

    @NotNull
    private Double discountPrice;

    @NotNull
    private Integer freeCvCount;

    private boolean isDiscount;

    @NotNull
    private Integer durationValue;

    @NotNull
    private DurationUnit durationUnit;

    @NotNull
    private Currency currency;


}
