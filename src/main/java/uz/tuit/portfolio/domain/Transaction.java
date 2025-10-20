package uz.tuit.portfolio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import uz.tuit.portfolio.model.Currency;
import uz.tuit.portfolio.model.TransactionStatus;
import uz.tuit.portfolio.model.TransactionType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Transaction extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    private Currency currency;

    private String description;

    private String cardNumber;

    private String cardExpiration;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    private String referenceCode; // bu tolov qilinadigan service dan keladigan kod masalan clickdagi tranzaksiyasini kodi

    private String cardHolderName;

    private Double fee; // Tranzaksiyaga qo‘shimcha komissiya (to‘lov) miqdori

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime processedAt; // Tranzaksiya amalga oshgan vaqtini bildiradi (odatda SUCCESS holatida to‘ldiriladi).

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
