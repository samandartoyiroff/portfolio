package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Subscription;
import uz.tuit.portfolio.dto.request.SubscriptionCreateDto;
import uz.tuit.portfolio.dto.request.SubscriptionUpdateDto;
import uz.tuit.portfolio.dto.response.SubscriptionResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubscriptionMapper {


    public Subscription toEntity(SubscriptionCreateDto subscriptionCreateDto) {
        Subscription subscription = new Subscription();

        subscription.setCurrency(subscriptionCreateDto.getCurrency());

        subscription.setDurationUnit(subscriptionCreateDto.getDurationUnit());

        subscription.setName(subscriptionCreateDto.getName());

        subscription.setDurationValue(subscriptionCreateDto.getDurationValue());

        subscription.setDiscount(subscriptionCreateDto.isDiscount());

        subscription.setFreeServices(subscriptionCreateDto.getFreeServices());

        subscription.setRealPrice(subscriptionCreateDto.getRealPrice());

        subscription.setFreeCvCount(subscriptionCreateDto.getFreeCvCount());

        if (subscriptionCreateDto.isDiscount()) {

            Double discountPrice = subscriptionCreateDto.getDiscountPrice();

            Double realPrice = subscriptionCreateDto.getRealPrice();

            subscription.setDiscountPrice(discountPrice);

            subscription.setDiscountPercentage((double) Math.round(100.0 - ((discountPrice / realPrice) * 100.0)));

        }

        else {
            subscription.setDiscountPrice(subscriptionCreateDto.getDiscountPrice());
            subscription.setDiscountPercentage(0.0);
        }

        return subscription;

    }

    public SubscriptionResponseDto toResponseDto(Subscription subscription) {

        SubscriptionResponseDto subscriptionResponseDto = new SubscriptionResponseDto();

        subscriptionResponseDto.setId(subscription.getId());

        subscriptionResponseDto.setName(subscription.getName());

        subscriptionResponseDto.setRealPrice(subscription.getRealPrice());

        subscriptionResponseDto.setDiscountPrice(subscription.getDiscountPrice());

        subscriptionResponseDto.setDiscountPercentage(subscription.getDiscountPercentage());

        subscriptionResponseDto.setFreeCvCount(subscription.getFreeCvCount());

        subscriptionResponseDto.setDiscount(subscription.isDiscount());

        subscriptionResponseDto.setDurationValue(subscription.getDurationValue());

        subscriptionResponseDto.setCurrency(subscription.getCurrency());

        subscriptionResponseDto.setDurationUnit(subscription.getDurationUnit());

        subscriptionResponseDto.setStatus(subscription.getStatus());

        subscriptionResponseDto.setCreatedDate(subscription.getCreatedAt());

        subscriptionResponseDto.setUpdatedDate(subscription.getUpdatedAt());

        return subscriptionResponseDto;



    }

    public List<SubscriptionResponseDto> toListResponseDto(List<Subscription> subscriptions) {

        return  subscriptions.stream().map(this::toResponseDto).collect(Collectors.toList());

    }

    public Subscription toUpdateEntity(Subscription subscription, SubscriptionUpdateDto subscriptionUpdateDto) {

        if (subscriptionUpdateDto.getName() != null && !subscriptionUpdateDto.getName().isBlank()) {
            subscription.setName(subscriptionUpdateDto.getName());
        }
        if (subscriptionUpdateDto.getRealPrice() != null) {
            subscription.setRealPrice(subscriptionUpdateDto.getRealPrice());
        }

        if (subscriptionUpdateDto.getIsDiscount() != null) {

            if (subscriptionUpdateDto.getIsDiscount()) {

                if (subscriptionUpdateDto.getDiscountPrice() != null) {

                    Double discountPrice = subscriptionUpdateDto.getDiscountPrice();

                    Double realPrice = subscriptionUpdateDto.getRealPrice()!=null ?  subscriptionUpdateDto.getRealPrice() : subscription.getRealPrice();

                    subscription.setDiscountPrice(discountPrice);

                    subscription.setDiscountPercentage( 100.0 - ( (discountPrice/realPrice) * 100.0) );

                    subscription.setDiscountPrice(subscriptionUpdateDto.getDiscountPrice());
                }

            }

            subscription.setDiscount(subscriptionUpdateDto.getIsDiscount());

        }


        if (subscriptionUpdateDto.getDurationUnit() != null) {
            subscription.setDurationUnit(subscriptionUpdateDto.getDurationUnit());
        }

        if (subscriptionUpdateDto.getDurationValue() != null) {
            subscription.setDurationValue(subscriptionUpdateDto.getDurationValue());
        }

        if (subscriptionUpdateDto.getFreeCvCount() != null) {
            subscription.setFreeCvCount(subscriptionUpdateDto.getFreeCvCount());
        }

        if (subscriptionUpdateDto.getFreeServices() != null && !subscriptionUpdateDto.getFreeServices().isEmpty()) {
            subscription.setFreeServices(subscriptionUpdateDto.getFreeServices());
        }

        if (subscriptionUpdateDto.getStatus() != null) {
            subscription.setStatus(subscriptionUpdateDto.getStatus());
        }
        return subscription;

    }
}
