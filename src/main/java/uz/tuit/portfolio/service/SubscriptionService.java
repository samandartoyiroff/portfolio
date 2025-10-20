package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.PaymentDto;
import uz.tuit.portfolio.dto.request.SubscriptionCreateDto;
import uz.tuit.portfolio.dto.request.SubscriptionUpdateDto;
import uz.tuit.portfolio.dto.response.SubscriptionResponseDto;
import uz.tuit.portfolio.dto.response.UserSubscriptionResponseDto;

import java.util.List;

public interface SubscriptionService {
    ResponseEntity<SubscriptionResponseDto> create(SubscriptionCreateDto subscriptionCreateDto);

    ResponseEntity<SubscriptionResponseDto> findById(Long id);


    ResponseEntity<List<SubscriptionResponseDto>> findAllActiveSubscription();


    ResponseEntity<List<SubscriptionResponseDto>> findAll();


    ResponseEntity<SubscriptionResponseDto> update(Long id, SubscriptionUpdateDto subscriptionUpdateDto);

    ResponseEntity<?> payForSubscription(User user, @Valid PaymentDto paymentDto);

    ResponseEntity<?> declineSubscription(Long userSubscriptionId, User user);

    ResponseEntity<List<UserSubscriptionResponseDto>> mySubscriptionsHistory(User user);

}
