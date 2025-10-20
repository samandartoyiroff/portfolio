package uz.tuit.portfolio.dto.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.model.SubscriptionStatus;

@Setter
@Getter
@ToString
public class EachSubscriptionSoldStatistics {

    private Long subscriptionId;

    private String subscriptionName;

    private SubscriptionStatus status;

    private Double totalIncome;

    private Double discountIncome;

    private Double realPriceIncome;

    private Integer count;

    private Integer discountSoldCount;

    private Integer realPriceSoldCount;



}
