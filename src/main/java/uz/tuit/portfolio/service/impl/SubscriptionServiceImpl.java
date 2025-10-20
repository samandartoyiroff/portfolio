package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.Subscription;
import uz.tuit.portfolio.domain.Transaction;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.domain.UserSubscription;
import uz.tuit.portfolio.dto.request.PaymentDto;
import uz.tuit.portfolio.dto.request.SubscriptionCreateDto;
import uz.tuit.portfolio.dto.request.SubscriptionUpdateDto;
import uz.tuit.portfolio.dto.response.SubscriptionResponseDto;
import uz.tuit.portfolio.dto.response.UserSubscriptionResponseDto;
import uz.tuit.portfolio.mapper.SubscriptionMapper;
import uz.tuit.portfolio.mapper.TransactionMapper;
import uz.tuit.portfolio.mapper.UserSubscriptionMapper;
import uz.tuit.portfolio.model.*;
import uz.tuit.portfolio.repository.SubscriptionRepository;
import uz.tuit.portfolio.repository.TransactionRepository;
import uz.tuit.portfolio.repository.UserSubscriptionRepository;
import uz.tuit.portfolio.service.ClickService;
import uz.tuit.portfolio.service.SubscriptionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionMapper subscriptionMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final ClickService clickService;
    private final TransactionRepository transactionRepository;
    private final UserSubscriptionMapper userSubscriptionMapper;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public ResponseEntity<SubscriptionResponseDto> create(SubscriptionCreateDto subscriptionCreateDto) {

        Subscription subscription = subscriptionMapper.toEntity(subscriptionCreateDto);

        subscriptionRepository.save(subscription);

        return ResponseEntity.ok(subscriptionMapper.toResponseDto(subscription));
    }

    @Override
    public ResponseEntity<SubscriptionResponseDto> findById(Long id) {

        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        return ResponseEntity.ok(subscriptionMapper.toResponseDto(subscription));

    }

    @Override
    public ResponseEntity<List<SubscriptionResponseDto>> findAllActiveSubscription() {

        List<Subscription> subscriptions = subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE);

        List<SubscriptionResponseDto> subscriptionResponseDtos = subscriptionMapper.toListResponseDto(subscriptions);

        return ResponseEntity.ok(subscriptionResponseDtos);

    }

    @Override
    public ResponseEntity<List<SubscriptionResponseDto>> findAll() {

        return ResponseEntity.ok(subscriptionMapper.toListResponseDto(subscriptionRepository.findAll()));

    }

    @Override
    @Transactional
    public ResponseEntity<SubscriptionResponseDto> update(Long id, SubscriptionUpdateDto subscriptionUpdateDto) {

        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscription = subscriptionMapper.toUpdateEntity(subscription, subscriptionUpdateDto);

        subscriptionRepository.save(subscription);

        return ResponseEntity.ok(subscriptionMapper.toResponseDto(subscription));


    }

    @Override
    @Transactional
    public ResponseEntity<?> payForSubscription(User user, PaymentDto paymentDto) {

        try{

            List<UserSubscription> userSubscriptions = userSubscriptionRepository.findByUserId(user.getId());

            userSubscriptions.forEach(userSubscription -> {
               if (userSubscription.checkStatus(UserSubscriptionStatus.ACTIVE)) throw new RuntimeException("User subscription already active");
            });

            Subscription subscription = subscriptionRepository.findById(paymentDto.getSubscriptionId()).orElseThrow(() -> new RuntimeException("Subscription not found"));

            if (clickService.payForSubscription(user, paymentDto, subscription)) {

                Double amount = subscription.isDiscount() ? subscription.getDiscountPrice() : subscription.getRealPrice();

                Transaction transaction = transactionMapper.toEntity(paymentDto,user, amount);

                System.out.println(transaction);

                transactionRepository.save(transaction);

                UserSubscription userSubscription = userSubscriptionMapper.toEntity(user, subscription);

                userSubscriptionRepository.save(userSubscription);

                return ResponseEntity.ok(userSubscriptionMapper.toResponseDto(userSubscription));

            }

            throw new RuntimeException("Payment failed");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw e; // rollback bo‘ladi, UnexpectedRollbackException bo‘lmaydi
        }

    }

    @Override
    @Transactional
    public ResponseEntity<?> declineSubscription(Long userSubscriptionId, User user) {


        UserSubscription userSubscription = userSubscriptionRepository.findById(userSubscriptionId).orElseThrow(() -> new RuntimeException("Subscription not found"));
        if (!userSubscription.getUser().getId().equals(user.getId())) throw new RuntimeException("User subscription not belong to this user");
        userSubscription.setSubscriptionStatus(UserSubscriptionStatus.CANCELLED);
        userSubscription.setEndDate(LocalDate.now());
        userSubscriptionRepository.save(userSubscription);
        return ResponseEntity.ok(userSubscriptionMapper.toResponseDto(userSubscription));

    }

    @Override
    public ResponseEntity<List<UserSubscriptionResponseDto>> mySubscriptionsHistory(User user) {

        List<UserSubscription> userSubscriptions = userSubscriptionRepository.findByUserId(user.getId());
        List<UserSubscriptionResponseDto> listDto = userSubscriptionMapper.toListDto(userSubscriptions);
        return ResponseEntity.ok(listDto);

    }
}
