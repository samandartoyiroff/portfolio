package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.PortfolioUpdateDto;
import uz.tuit.portfolio.dto.request.ProjectUpdateDto;

public interface PortfolioService {
    ResponseEntity<?> removeHobby(String hobby, User user);

    ResponseEntity<?> addHobby(String hobby, User user);


    ResponseEntity<?> updatePortfolio(PortfolioUpdateDto portfolioUpdateDto, MultipartFile portfolioImage, User user);

    ResponseEntity<?> getMyPortfolio(User user);

}
