package uz.tuit.portfolio.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OTPCode extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String email;

    private LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(3);

}
