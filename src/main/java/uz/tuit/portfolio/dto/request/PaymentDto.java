package uz.tuit.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PaymentDto {

    @NotBlank
    private String cardNumber;

    @NotBlank
    private String expirationDate;

    private String cvv;

    private String cardHolderName;

    private String description;

    private String idempotencyKey;

    private Long subscriptionId;

}
