package uz.tuit.portfolio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.tuit.portfolio.model.UserSubscriptionStatus;
import uz.tuit.portfolio.service.StatisticsService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/sold-subscription")
    public ResponseEntity<?> getSoldSubscriptionStatistics(
            @RequestParam(name = "from", required = false)LocalDate from,
            @RequestParam(name = "to", required = false)LocalDate to,
            @RequestParam(name = "subscriptionId", required = false)Long subscriptionId,
            @RequestParam(name = "status", required = false)UserSubscriptionStatus status
            ) {
        return statisticsService.soldStatistics(from, to, subscriptionId, status);
    }

}
