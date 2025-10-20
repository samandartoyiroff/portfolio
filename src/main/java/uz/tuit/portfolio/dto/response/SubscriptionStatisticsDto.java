package uz.tuit.portfolio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class SubscriptionStatisticsDto {

    private Double totalIncome;

    private Double realPriceTotalIncome;

    private Double discountPriceTotalIncome;

    private Integer soldSubscriptionsCount;

    private Integer realPriceSubscriptionsCount;

    private Integer discountPriceSoldSubscriptionsCount;

    private List<EachSubscriptionSoldStatistics> eachSubscriptionSoldStatistics;

    private List<UserSubscriptionStatisticsDto> subscriptions;

}
