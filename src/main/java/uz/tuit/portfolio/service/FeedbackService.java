package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.FeedbackCreateDto;

public interface FeedbackService {

    ResponseEntity<?> addFeedback(@Valid FeedbackCreateDto feedbackCreateDto, User toUser);

    ResponseEntity<?> deleteFeedback(Long id, User user);

}
