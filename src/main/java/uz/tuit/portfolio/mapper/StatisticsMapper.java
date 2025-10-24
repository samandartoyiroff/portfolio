package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Subscription;
import uz.tuit.portfolio.domain.UserSubscription;
import uz.tuit.portfolio.dto.response.EachSubscriptionSoldStatistics;
import uz.tuit.portfolio.dto.response.SubscriptionStatisticsDto;
import uz.tuit.portfolio.dto.response.UserSubscriptionStatisticsDto;
import uz.tuit.portfolio.repository.SubscriptionRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatisticsMapper {


    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionStatisticsDto toSubscriptionStatistics(List<UserSubscription> userSubscriptions) {

        SubscriptionStatisticsDto subscriptionStatisticsDto = new SubscriptionStatisticsDto();

        List<EachSubscriptionSoldStatistics> eachSubscriptionSoldStatistics = new ArrayList<>();
        List<UserSubscriptionStatisticsDto> subscriptions = new ArrayList<>();

        double totalIncome = 0.0;
        double realPriceTotalIncome = 0.0;
        double discountPriceTotalIncome = 0.0;

        int soldSubscriptionsCount = 0;
        int realPriceSubscriptionsCount = 0;
        int discountPriceSoldSubscriptionsCount = 0;

        // 🔹 1. UserSubscription ma’lumotlarini tahlil qilish
        for (UserSubscription us : userSubscriptions) {

            if (us.isDiscount()) {
                discountPriceTotalIncome += us.getDiscountPrice();
                discountPriceSoldSubscriptionsCount++;
            } else {
                realPriceTotalIncome += us.getPrice();
                realPriceSubscriptionsCount++;
            }
            soldSubscriptionsCount++;

            UserSubscriptionStatisticsDto userDto = new UserSubscriptionStatisticsDto();
            userDto.setId(us.getId());
            userDto.setUserId(us.getUser().getId());
            userDto.setSubscriptionId(us.getSubscription().getId());
            userDto.setFullName(us.getUser().getFullName());
            userDto.setEmail(us.getUser().getEmail());
            userDto.setPhoneNumber(us.getUser().getPhoneNumber());
            userDto.setProfilePhotoUrl(
                    us.getUser().getProfilePhoto() != null ? us.getUser().getProfilePhoto().getPath() : null
            );
            userDto.setIsSubscribed(us.getUser().getIsSubscriber());
            userDto.setCvUrl(us.getUser().getCvUrl());
            userDto.setGender(us.getUser().getGender());

            subscriptions.add(userDto);
        }

        // 🔹 2. Har bir Subscription bo‘yicha statistikani hisoblash
        List<Subscription> subscriptionList = subscriptionRepository.findAll();

        subscriptionList.forEach(subscription -> {

            EachSubscriptionSoldStatistics stat = new EachSubscriptionSoldStatistics();
            stat.setSubscriptionId(subscription.getId());
            stat.setSubscriptionName(subscription.getName());
            stat.setStatus(subscription.getStatus());

            // 🔧 To‘g‘rilangan filter (subscription.id bilan solishtirish kerak)
            List<UserSubscription> relatedSubs = userSubscriptions.stream()
                    .filter(us -> us.getSubscription().getId().equals(subscription.getId()))
                    .toList();

            stat.setCount(relatedSubs.size());

            double discountIncome = 0.0;
            double realPriceIncome = 0.0;
            int discountSoldCount = 0;
            int realPriceSoldCount = 0;

            for (UserSubscription us : relatedSubs) {
                if (us.isDiscount()) {
                    discountIncome += us.getDiscountPrice(); // ✅ qo‘shish kerak
                    discountSoldCount++;
                } else {
                    realPriceIncome += us.getPrice(); // ✅ qo‘shish kerak
                    realPriceSoldCount++;
                }
            }

            double totalUserSubsIncome = discountIncome + realPriceIncome;

            stat.setTotalIncome(totalUserSubsIncome);
            stat.setRealPriceIncome(realPriceIncome);
            stat.setDiscountIncome(discountIncome);
            stat.setDiscountSoldCount(discountSoldCount);
            stat.setRealPriceSoldCount(realPriceSoldCount);

            eachSubscriptionSoldStatistics.add(stat);
        });

        // 🔹 3. Umumiy statistika
        totalIncome = discountPriceTotalIncome + realPriceTotalIncome;

        subscriptionStatisticsDto.setSubscriptions(subscriptions);
        subscriptionStatisticsDto.setTotalIncome(totalIncome);
        subscriptionStatisticsDto.setRealPriceTotalIncome(realPriceTotalIncome);
        subscriptionStatisticsDto.setDiscountPriceTotalIncome(discountPriceTotalIncome);
        subscriptionStatisticsDto.setRealPriceSubscriptionsCount(realPriceSubscriptionsCount);
        subscriptionStatisticsDto.setSoldSubscriptionsCount(soldSubscriptionsCount);
        subscriptionStatisticsDto.setDiscountPriceSoldSubscriptionsCount(discountPriceSoldSubscriptionsCount);
        subscriptionStatisticsDto.setEachSubscriptionSoldStatistics(eachSubscriptionSoldStatistics);

        return subscriptionStatisticsDto;
    }

}
