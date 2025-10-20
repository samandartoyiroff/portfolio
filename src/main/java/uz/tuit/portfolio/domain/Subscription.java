package uz.tuit.portfolio.domain;

import jakarta.persistence.*;
import lombok.*;
import uz.tuit.portfolio.model.Currency;
import uz.tuit.portfolio.model.DurationUnit;
import uz.tuit.portfolio.model.SubscriptionStatus;

import java.time.Duration;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Subscription extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private Double realPrice;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "subscription_free_service", joinColumns = @JoinColumn(name = "subscription_id"))
    @Column(name = "free_services")
    private List<String> freeServices;

    private Double discountPrice;

    private Double discountPercentage;

    private Integer freeCvCount;

    private boolean isDiscount;

    private Integer durationValue;

    private Currency currency;

    private DurationUnit durationUnit;

    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

}
