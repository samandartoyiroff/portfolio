package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Subscription;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.domain.UserSubscription;
import uz.tuit.portfolio.dto.response.SubscriptionResponseDto;
import uz.tuit.portfolio.dto.response.UserSubscriptionResponseDto;
import uz.tuit.portfolio.model.SubscriptionStatus;
import uz.tuit.portfolio.model.UserSubscriptionStatus;
import uz.tuit.portfolio.util.TimeUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserSubscriptionMapper {

    private final TimeUtil timeUtil;

    public UserSubscription toEntity(User user, Subscription subscription) {

        UserSubscription  userSubscription = new UserSubscription();

        userSubscription.setUser(user);

        userSubscription.setSubscription(subscription);

        userSubscription.setName(subscription.getName());

        userSubscription.setPrice(subscription.getRealPrice());

        userSubscription.setDiscountPrice(subscription.getDiscountPrice());

        userSubscription.setDiscountPercentage(subscription.getDiscountPercentage());

        userSubscription.setFreeCvCount(subscription.getFreeCvCount());

        userSubscription.setRemainingCvCount(subscription.getFreeCvCount());

        userSubscription.setDiscount(subscription.isDiscount());

        userSubscription.setCurrency(subscription.getCurrency());

        userSubscription.setDurationValue(subscription.getDurationValue());

        userSubscription.setDurationUnit(subscription.getDurationUnit());

        userSubscription.setStartDate(LocalDate.now());

        userSubscription.setEndDate(timeUtil.toExpireDate(LocalDate.now(), userSubscription.getDurationUnit(), userSubscription.getDurationValue()));

        userSubscription.setRemainingDays(timeUtil.remainingDays(userSubscription.getDurationUnit(), userSubscription.getDurationValue()));

        userSubscription.setSubscriptionStatus(UserSubscriptionStatus.ACTIVE);

        userSubscription.setCurrency(subscription.getCurrency());

        return userSubscription;


    }

    public UserSubscriptionResponseDto toResponseDto(UserSubscription userSubscription){
        UserSubscriptionResponseDto userSubscriptionResponseDto = new UserSubscriptionResponseDto(

                userSubscription.getId(),
                userSubscription.getName(),
                userSubscription.getSubscription().getId(),
                userSubscription.getUser().getId(),
                userSubscription.getPrice(),
                userSubscription.getDiscountPrice(),
                userSubscription.getDiscountPercentage(),
                userSubscription.getFreeCvCount(),
                userSubscription.getRemainingCvCount(),
                userSubscription.getCurrency(),
                userSubscription.isDiscount(),
                userSubscription.getDurationValue(),
                userSubscription.getDurationUnit(),
                userSubscription.getStartDate(),
                userSubscription.getEndDate(),
                userSubscription.getRemainingDays(),
                userSubscription.getSubscriptionStatus()
        );

        userSubscriptionResponseDto.setCreatedDate(userSubscription.getSubscription().getCreatedAt());
        userSubscriptionResponseDto.setUpdatedDate(userSubscription.getSubscription().getUpdatedAt());
        userSubscriptionResponseDto.setCreatedBy(userSubscription.getUser().getId());
        userSubscriptionResponseDto.setUpdatedBy(userSubscription.getUser().getId());

        return userSubscriptionResponseDto;

    }

    public List<UserSubscriptionResponseDto> toListDto(List<UserSubscription> userSubscriptions) {
        return userSubscriptions.stream().map(this::toResponseDto).collect(Collectors.toList());
    }
}
