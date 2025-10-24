package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Address;
import uz.tuit.portfolio.domain.ContactInfo;
import uz.tuit.portfolio.domain.Portfolio;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.PortfolioUpdateDto;
import uz.tuit.portfolio.dto.response.PortfolioResponseDto;

@Component
@RequiredArgsConstructor
public class PortfolioMapper {


    private final ExperienceMapper experienceMapper;
    private final SoftSkillMapper softSkillMapper;
    private final HardSkillMapper hardSkillMapper;
    private final EducationMapper educationMapper;
    private final TechnologyMapper technologyMapper;
    private final CertificateMapper certificateMapper;
    private final LanguageSkillMapper languageSkillMapper;
    private final ProjectMapper projectMapper;
    private final CVImageMapper cVImageMapper;
    private final FeedbackMapper feedbackMapper;

    public PortfolioResponseDto toPortfolioResponseDto(User user) {

        Portfolio cv = user.getPortfolio();

        if (cv == null) return null;

        PortfolioResponseDto cvResponseDto = new PortfolioResponseDto();

        cvResponseDto.setId(cv.getId());

        cvResponseDto.setUserId(user.getId());

        cvResponseDto.setFullName(cv.getFullName());

        cvResponseDto.setGender(user.getGender());

        cvResponseDto.setEmail(cv.getEmail());

        cvResponseDto.setPhoneNumber( cv.getPhoneNumber());

        cvResponseDto.setAboutMe( cv.getAboutMe());

        cvResponseDto.setAddress( cv.getAddress());

        cvResponseDto.setGender(user.getGender());

        cvResponseDto.setContactInfo(cv.getContactInfo());

        cvResponseDto.setHobbies(cv.getHobbies());

        cvResponseDto.setOccupation(cv.getOccupation());

        cvResponseDto.setCvImage(
                cVImageMapper.toResponseDto(cv.getPortifolioImage()!=null ? cv.getPortifolioImage() : null)
        );

        cvResponseDto.setSoftSkills(
                softSkillMapper.toListResponseDto(cv.getSoftSkills())
        );

        cvResponseDto.setHardSkills(
                hardSkillMapper.toListResponse(cv.getHardSkills())
        );

        cvResponseDto.setTechnologies(
                technologyMapper.toListResponse(cv.getTechnologies())
        );

        cvResponseDto.setProjects(
                projectMapper.toListDto(cv.getProjects())
        );

        cvResponseDto.setLanguages(
                languageSkillMapper.toListResponse(cv.getLanguageSkills())
        );

        cvResponseDto.setExperience(
                experienceMapper.toListResponse(cv.getExperiences())
        );

        cvResponseDto.setEducations(
                educationMapper.toListResponse(cv.getEducations())
        );

        cvResponseDto.setCertificates(
                certificateMapper.toListResponse(cv.getCertificates())
        );

        cvResponseDto.setFeedbacks(
                feedbackMapper.toListResponse(cv.getFeedbacks())
        );

        cvResponseDto.setDriverLicense(cv.getDriverLicense());

        return cvResponseDto;

    }

    public Portfolio updateEntity(Portfolio portfolio, User user, PortfolioUpdateDto portfolioUpdateDto) {

        if (portfolioUpdateDto.getAboutMe()!=null && !portfolioUpdateDto.getAboutMe().isBlank()) {
            portfolio.setAboutMe(portfolioUpdateDto.getAboutMe());
        }
        if (portfolioUpdateDto.getFullName()!=null && !portfolioUpdateDto.getFullName().isBlank()) {
            portfolio.setFullName(portfolioUpdateDto.getFullName());
        }
        if (portfolioUpdateDto.getEmail()!=null && !portfolioUpdateDto.getEmail().isBlank()) {
            portfolio.setEmail(portfolioUpdateDto.getEmail());
        }
        if (portfolioUpdateDto.getPhoneNumber()!=null && !portfolioUpdateDto.getPhoneNumber().isBlank()) {
            portfolio.setPhoneNumber(portfolioUpdateDto.getPhoneNumber());
        }
        if (portfolioUpdateDto.getDriverLicense()!=null && !portfolioUpdateDto.getDriverLicense().isBlank()) {
            portfolio.setDriverLicense(portfolioUpdateDto.getDriverLicense());
        }
        if (portfolioUpdateDto.getGender()!=null){
            portfolio.setGender(portfolioUpdateDto.getGender());
        }

        if (portfolioUpdateDto.getContactInfo()!=null){

            ContactInfo contactInfo = portfolioUpdateDto.getContactInfo();

            if (contactInfo.getTelegramUsername()!=null && !contactInfo.getTelegramUsername().isBlank()) {
                portfolio.getContactInfo().setTelegramUsername(contactInfo.getTelegramUsername());
            }
            if (contactInfo.getContactEmail()!=null && !contactInfo.getContactEmail().isBlank()) {
                portfolio.getContactInfo().setContactEmail(contactInfo.getContactEmail());
            }
            if(contactInfo.getContactPhoneNumber()!=null && !contactInfo.getContactPhoneNumber().isBlank()){
                portfolio.getContactInfo().setContactPhoneNumber(contactInfo.getContactPhoneNumber());
            }
            if(contactInfo.getInstagramUsername()!=null && !contactInfo.getInstagramUsername().isBlank()){
                portfolio.getContactInfo().setInstagramUsername(contactInfo.getInstagramUsername());
            }
            if (contactInfo.getFacebookLink()!=null && !contactInfo.getFacebookLink().isBlank()) {
                portfolio.getContactInfo().setFacebookLink(contactInfo.getFacebookLink());
            }
            if (contactInfo.getTwitterLink()!=null && !contactInfo.getTwitterLink().isBlank()) {
                portfolio.getContactInfo().setTwitterLink(contactInfo.getTwitterLink());
            }
            if (contactInfo.getLinkedinLink()!=null && !contactInfo.getLinkedinLink().isBlank()) {
                portfolio.getContactInfo().setLinkedinLink(contactInfo.getLinkedinLink());
            }
            if (contactInfo.getLeetcodeLink()!=null && !contactInfo.getLeetcodeLink().isBlank()) {
                portfolio.getContactInfo().setLeetcodeLink(contactInfo.getLeetcodeLink());
            }
            if (contactInfo.getHeadHunterLink()!=null && !contactInfo.getHeadHunterLink().isBlank()) {
                portfolio.getContactInfo().setHeadHunterLink(contactInfo.getHeadHunterLink());
            }

        }

        if (portfolioUpdateDto.getAddress()!=null){

            Address address = portfolioUpdateDto.getAddress();

            if (address.getAddress()!=null && !address.getAddress().isBlank()) {
                portfolio.getAddress().setAddress(address.getAddress());
            }
            if (address.getZipCode()!=null && !address.getZipCode().isBlank()) {
                portfolio.getAddress().setZipCode(address.getZipCode());
            }
            if (address.getCityTown()!=null && !address.getCityTown().isBlank()) {
                portfolio.getAddress().setCityTown(address.getCityTown());
            }

        }

        return portfolio;

    }
}
