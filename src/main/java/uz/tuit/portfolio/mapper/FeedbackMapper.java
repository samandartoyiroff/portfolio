package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Feedback;
import uz.tuit.portfolio.domain.Image;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.response.FeedbackResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FeedbackMapper {

    public FeedbackResponseDto toResponseDto (Feedback feedback) {

        User fromUser = feedback.getFromUser();
        User toUser = feedback.getToUser();

        FeedbackResponseDto feedbackResponseDto = new FeedbackResponseDto();

        feedbackResponseDto.setId(feedback.getId());

        feedbackResponseDto.setFeedback(feedback.getFeedback());

        feedbackResponseDto.setFromFullName(fromUser.getFullName());

        feedbackResponseDto.setToFullName(toUser.getFullName());

        feedbackResponseDto.setCreatedDate(feedback.getCreatedAt());

        feedbackResponseDto.setUpdatedDate(feedback.getUpdatedAt());



        if (fromUser.getProfilePhoto() != null) {
            Image profilePhoto = fromUser.getProfilePhoto();
            feedbackResponseDto.setFromImageUrl(profilePhoto.getPath());
            feedbackResponseDto.setFromImageId(profilePhoto.getId());
        }

        if (toUser.getProfilePhoto() != null) {
            Image profilePhoto = toUser.getProfilePhoto();
            feedbackResponseDto.setToImageUrl(profilePhoto.getPath());
            feedbackResponseDto.setToImageId(profilePhoto.getId());
        }
        return feedbackResponseDto;


    }

    public List<FeedbackResponseDto> toListResponse(List<Feedback> feedbacks) {

        return feedbacks.stream().map(this::toResponseDto).collect(Collectors.toList());

    }
}
