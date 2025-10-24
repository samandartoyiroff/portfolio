package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.Image;
import uz.tuit.portfolio.domain.Portfolio;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.PortfolioUpdateDto;
import uz.tuit.portfolio.dto.request.ProjectUpdateDto;
import uz.tuit.portfolio.dto.response.PortfolioResponseDto;
import uz.tuit.portfolio.mapper.PortfolioMapper;
import uz.tuit.portfolio.repository.PortfolioRepository;
import uz.tuit.portfolio.repository.UserRepository;
import uz.tuit.portfolio.service.ImageService;
import uz.tuit.portfolio.service.PortfolioService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final ImageService imageService;

    @Override
    @Transactional
    public ResponseEntity<?> removeHobby(String hobby, User user) {

        Portfolio portfolio = user.getPortfolio();

        portfolioRepository.deleteHobby(portfolio.getId(), hobby);

        return ResponseEntity.ok("Hobby Removed");


    }

    @Override
    @Transactional
    public ResponseEntity<?> addHobby(String hobby, User user) {
        Portfolio portfolio = user.getPortfolio();

        List<String> hobbies = portfolio.getHobbies();

        if (hobbies ==null) {

            hobbies = new ArrayList<>();
            portfolio.setHobbies(hobbies);

        }

        if (
                hobbies.contains(hobby)
        ){
            return  ResponseEntity.badRequest().body("Hobby already exists");
        }

        hobbies.add(hobby);
        portfolio.setHobbies(hobbies);
        user.setPortfolio(portfolio);
        userRepository.save(user);
        return ResponseEntity.ok("Hobby has been added to Portfolio");



    }
    @Override
    @Transactional
    public ResponseEntity<?> updatePortfolio(PortfolioUpdateDto portfolioUpdateDto, MultipartFile portfolioImage, User user) {

        Portfolio portfolio = user.getPortfolio();

        if (portfolioImage!=null) {

            Image image = imageService.updateImage(portfolioImage, portfolio.getPortifolioImage());
            portfolio.setPortifolioImage(image);

        }

        portfolio = portfolioMapper.updateEntity(portfolio, user, portfolioUpdateDto);

        portfolioRepository.save(portfolio);

        return ResponseEntity.ok(portfolioMapper.toPortfolioResponseDto(user));

    }

    @Override
    public ResponseEntity<?> getMyPortfolio(User user) {
        PortfolioResponseDto portfolioResponseDto = portfolioMapper.toPortfolioResponseDto(user);
        return ResponseEntity.ok(portfolioResponseDto);
    }
}
