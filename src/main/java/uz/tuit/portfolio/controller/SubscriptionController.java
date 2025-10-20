package uz.tuit.portfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.PaymentDto;
import uz.tuit.portfolio.dto.request.SubscriptionCreateDto;
import uz.tuit.portfolio.dto.request.SubscriptionUpdateDto;
import uz.tuit.portfolio.dto.response.SubscriptionResponseDto;
import uz.tuit.portfolio.dto.response.UserSubscriptionResponseDto;
import uz.tuit.portfolio.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/create")
    public ResponseEntity<SubscriptionResponseDto> createSubscription(
            @RequestBody SubscriptionCreateDto subscriptionCreateDto
    ) {
        return subscriptionService.create(subscriptionCreateDto);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<SubscriptionResponseDto> findById(
            @PathVariable(name = "id") Long id
    ){
        return subscriptionService.findById(id);
    }

    @GetMapping("/findAllActiveSubscription") // for user
    public ResponseEntity<List<SubscriptionResponseDto>> findAllActiveSubscription() {
        return subscriptionService.findAllActiveSubscription();
    }

    @GetMapping("/findAll") // for admin
    public ResponseEntity<List<SubscriptionResponseDto>> findAll() {
        return subscriptionService.findAll();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<SubscriptionResponseDto> updateSubscription(
            @RequestBody SubscriptionUpdateDto subscriptionUpdateDto,
            @PathVariable Long id
    ){
        return subscriptionService.update(id, subscriptionUpdateDto);
    }

    @PostMapping("/buy-subscription")
    public ResponseEntity<?> buySubscription(
            @RequestBody @Valid PaymentDto paymentDto,
            @AuthenticationPrincipal User user
    ){
        return subscriptionService.payForSubscription(user, paymentDto);
    }

    @PostMapping("/cancel-subscription/{userSubscriptionId}")
    public ResponseEntity<?> declineSubscription(
            @PathVariable Long userSubscriptionId,
            @AuthenticationPrincipal User user
    ){
        return subscriptionService.declineSubscription(userSubscriptionId, user);
    }

    @GetMapping("/my-subscriptions-history")
    public ResponseEntity<List<UserSubscriptionResponseDto>> findAllMySubscriptions(
            @AuthenticationPrincipal User user
    ){
        return subscriptionService.mySubscriptionsHistory(user);
    }


}
