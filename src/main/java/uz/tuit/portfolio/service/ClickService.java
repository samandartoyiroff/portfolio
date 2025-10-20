package uz.tuit.portfolio.service;

import uz.tuit.portfolio.domain.Subscription;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.PaymentDto;

public interface ClickService {

    Boolean payForSubscription(User user, PaymentDto paymentDto, Subscription subscription);

}
