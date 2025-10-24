package uz.tuit.portfolio.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.domain.Address;
import uz.tuit.portfolio.domain.ContactInfo;
import uz.tuit.portfolio.model.Gender;

import java.util.List;

@Setter
@Getter
@ToString
public class PortfolioResponseDto {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String occupation;

    private String aboutMe;

    private Gender gender;

    private ContactInfo contactInfo;

    private Address address;

    private CVImageResponseDto cvImage;

    private List<String> hobbies;

    private List<SoftSkillResponseDto> softSkills;

    private List<HardSkillResponseDto> hardSkills;

    private List<TechnologyResponseDto> technologies;

    private List<ProjectResponseDto> projects;

    private List<LanguageSkillResponseDto> languages;

    private List<ExperienceResponseDto>  experience;

    private List<EducationResponseDto> educations;

    private List<CertificateResponseDto> certificates;

    private List<FeedbackResponseDto> feedbacks;

    private String driverLicense;

}
