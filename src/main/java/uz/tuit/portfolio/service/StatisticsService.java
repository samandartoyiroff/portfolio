package uz.tuit.portfolio.service;

import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.model.UserSubscriptionStatus;

import java.time.LocalDate;

public interface StatisticsService {
    ResponseEntity<?> soldStatistics(LocalDate from, LocalDate to, Long subscriptionId, UserSubscriptionStatus status);

}
