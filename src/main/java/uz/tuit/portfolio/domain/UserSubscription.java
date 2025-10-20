package uz.tuit.portfolio.domain;

import jakarta.persistence.*;
import lombok.*;
import uz.tuit.portfolio.model.Currency;
import uz.tuit.portfolio.model.DurationUnit;
import uz.tuit.portfolio.model.SubscriptionStatus;
import uz.tuit.portfolio.model.UserSubscriptionStatus;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Subscription subscription;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false)
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

    @Enumerated(EnumType.STRING)
    private UserSubscriptionStatus subscriptionStatus =  UserSubscriptionStatus.ACTIVE;

    public boolean checkStatus(UserSubscriptionStatus userSubscriptionStatus) {

        return this.subscriptionStatus.equals(userSubscriptionStatus);

    }
}
