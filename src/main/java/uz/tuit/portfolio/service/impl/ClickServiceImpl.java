package uz.tuit.portfolio.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.Subscription;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.PaymentDto;
import uz.tuit.portfolio.service.ClickService;

@Service
@RequiredArgsConstructor
public class ClickServiceImpl implements ClickService {
    @Override
    public Boolean payForSubscription(User user, PaymentDto paymentDto, Subscription subscription) {
        return true;
    }
}
