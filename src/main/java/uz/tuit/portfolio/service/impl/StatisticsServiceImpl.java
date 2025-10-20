package uz.tuit.portfolio.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.UserSubscription;
import uz.tuit.portfolio.dto.response.SubscriptionStatisticsDto;
import uz.tuit.portfolio.mapper.StatisticsMapper;
import uz.tuit.portfolio.model.UserSubscriptionStatus;
import uz.tuit.portfolio.repository.UserSubscriptionRepository;
import uz.tuit.portfolio.service.StatisticsService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final StatisticsMapper statisticsMapper;

    @Override
    public ResponseEntity<?> soldStatistics(LocalDate from, LocalDate to, Long subscriptionId, UserSubscriptionStatus status) {

        LocalDate fromTime = from == null
                ? LocalDate.of(2000, 1,1)
                : from;

        LocalDate toTime = to == null
                ? LocalDate.of(3000, 1,1)
                : to;

        List<UserSubscription> userSubscriptions = userSubscriptionRepository.filterSubscription(
                fromTime,
                toTime,
                subscriptionId,
                status
        );

        SubscriptionStatisticsDto subscriptionStatisticsDto = statisticsMapper.toSubscriptionStatistics(userSubscriptions);

        return ResponseEntity.ok(subscriptionStatisticsDto);

    }
}
