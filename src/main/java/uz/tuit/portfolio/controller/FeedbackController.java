package uz.tuit.portfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.FeedbackCreateDto;
import uz.tuit.portfolio.service.FeedbackService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/give-feedback")
    public ResponseEntity<?> giveFeedback(
            @RequestBody @Valid FeedbackCreateDto feedbackCreateDto,
            @AuthenticationPrincipal User fromUser
            ){
        return feedbackService.addFeedback(feedbackCreateDto, fromUser);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteFeedback(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return feedbackService.deleteFeedback(id, user);
    }

}
