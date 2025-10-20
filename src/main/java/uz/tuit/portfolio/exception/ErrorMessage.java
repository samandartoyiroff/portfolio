package uz.tuit.portfolio.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ErrorMessage {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ErrorMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

}
