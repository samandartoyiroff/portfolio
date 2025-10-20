package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Transaction;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.PaymentDto;
import uz.tuit.portfolio.model.Currency;
import uz.tuit.portfolio.model.TransactionStatus;
import uz.tuit.portfolio.model.TransactionType;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TransactionMapper {
    public Transaction  toEntity(PaymentDto paymentDto, User user, Double amount) {

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setAmount(amount);
        transaction.setCurrency(Currency.UZS);
        transaction.setDescription(paymentDto.getDescription());
        transaction.setCardNumber(paymentDto.getCardNumber());
        transaction.setCardExpiration(paymentDto.getExpirationDate());
        transaction.setCardHolderName(paymentDto.getCardHolderName());
        transaction.setIdempotencyKey(paymentDto.getIdempotencyKey());
        transaction.setTransactionType(TransactionType.BUY_SUBSCRIPTION);
        transaction.setStatus(TransactionStatus.PASSED);
        transaction.setProcessedAt(LocalDateTime.now());
        transaction.setReferenceCode("ReferenceCode");
        return transaction;

    }
}
