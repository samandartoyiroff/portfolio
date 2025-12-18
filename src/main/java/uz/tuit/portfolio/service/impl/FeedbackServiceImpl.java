package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.tuit.portfolio.domain.Feedback;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.FeedbackCreateDto;
import uz.tuit.portfolio.mapper.FeedbackMapper;
import uz.tuit.portfolio.repository.FeedbackRepository;
import uz.tuit.portfolio.repository.UserRepository;
import uz.tuit.portfolio.service.FeedbackService;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {


    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Override
    @Transactional
    public ResponseEntity<?> addFeedback(FeedbackCreateDto feedbackCreateDto, User fromUser) {

        Feedback feedback = new Feedback();

        Long toUserId = feedbackCreateDto.getToUser();

        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        feedback.setToUser(toUser);
        feedback.setFeedback(feedbackCreateDto.getFeedback());
        feedback.setFromUser(fromUser);
        feedback.setPortfolio(toUser.getPortfolio());

        feedbackRepository.save(feedback);

        // Portfolioga beriladi

        return ResponseEntity.ok(feedbackMapper.toResponseDto(feedback));

    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteFeedback(Long id, User user) {

        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Feedback not found"));

        if (!feedback.getToUser().getId().equals(user.getId())) throw new IllegalArgumentException("Feedback not belong to this user");

        feedback.setPortfolio(null);

        feedbackRepository.delete(feedback);

        return ResponseEntity.ok("Feedback deleted successfully");


    }
}
