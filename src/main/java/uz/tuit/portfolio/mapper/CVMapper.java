package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.*;
import uz.tuit.portfolio.dto.request.CVCreateDto;
import uz.tuit.portfolio.dto.request.CVUpdateDto;
import uz.tuit.portfolio.dto.response.CVResponseDto;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.FeedbackRepository;
import uz.tuit.portfolio.repository.UserRepository;
import uz.tuit.portfolio.service.ImageService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CVMapper {


    private final ImageService imageService;
    private final ExperienceMapper experienceMapper;
    private final SoftSkillMapper softSkillMapper;
    private final HardSkillMapper hardSkillMapper;
    private final EducationMapper educationMapper;
    private final TechnologyMapper technologyMapper;
    private final CertificateMapper certificateMapper;
    private final LanguageSkillMapper languageSkillMapper;
    private final ProjectMapper projectMapper;
    private final CVRepository cVRepository;
    private final CVImageMapper cVImageMapper;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public CVResponseDto toResponseDto(CV cv, User user) {

        CVResponseDto cvResponseDto = new CVResponseDto();

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
                cVImageMapper.toResponseDto(cv.getCvPhoto()!=null ? cv.getCvPhoto() : null)
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

        cvResponseDto.setDriverLicense(cv.getDriverLicense());

        return cvResponseDto;

    }

    public CV toEntity(CVCreateDto cvCreateDto, MultipartFile cvImage) {

        CV cv = new CV();

        cv.setFullName(cvCreateDto.getFullName());

        cv.setEmail(cvCreateDto.getEmail());

        cv.setPhoneNumber(cvCreateDto.getPhoneNumber());

        Image image = imageService.uploadImage(cvImage);

        cv.setCvPhoto(image);

        cv.setAddress(cvCreateDto.getAddress());

        cv.setAboutMe(cvCreateDto.getAboutMe());

        cv.setHobbies(cvCreateDto.getHobbies());

        cv.setContactInfo(cvCreateDto.getContactInfo());

        cv.setOccupation(cvCreateDto.getOccupationName());

        List<Experience> experiences = experienceMapper.toListEntity(cvCreateDto.getExperience());

        cv.setExperiences(experiences);

        List<SoftSkill> softSkills = softSkillMapper.toListEntity(cvCreateDto.getSoftSkillIds());

        cv.setSoftSkills(softSkills);

        List<HardSkill> hardSkills = hardSkillMapper.toListEntity(cvCreateDto.getHardSkillIds());

        cv.setHardSkills(hardSkills);

        List<Education> educations = educationMapper.toListEntity(cvCreateDto.getEducations());

        cv.setEducations(educations);

        List<Technology> technologies = technologyMapper.toListEntity(cvCreateDto.getTechnologySkillIds());

        cv.setTechnologies(technologies);

        List<Certificate> certificates = certificateMapper.toListEntity(cvCreateDto.getCertificates());

        cv.setCertificates(certificates);

        List<LanguageSkill> languageSkills = languageSkillMapper.toListEntity(cvCreateDto.getLanguages());

        cv.setLanguageSkills(languageSkills);

        List<Project> projects = projectMapper.toListEntity(cvCreateDto.getProjects());

        cv.setProjects(projects);

        cv.setDriverLicense(cvCreateDto.getDriverLicense());

        return cVRepository.save(cv);

    }


    public CV updateCv(CVUpdateDto cvUpdateDto, CV cv) {

        if(cvUpdateDto.getFullName() != null && !cvUpdateDto.getFullName().isBlank()){
            cv.setFullName(cvUpdateDto.getFullName());
        }
        if(cvUpdateDto.getEmail() != null && !cvUpdateDto.getEmail().isBlank()){
            cv.setEmail(cvUpdateDto.getEmail());
        }
        if(cvUpdateDto.getPhoneNumber() != null && !cvUpdateDto.getPhoneNumber().isBlank()){
            cv.setPhoneNumber(cvUpdateDto.getPhoneNumber());
        }
        if (cvUpdateDto.getAddress() != null) {

            if (cv.getAddress() == null) {
                cv.setAddress(new Address());
            }

            Address address = cvUpdateDto.getAddress();

            if (address.getAddress() != null && !address.getAddress().isBlank()) {
                cv.getAddress().setAddress(address.getAddress());
            }

            if (address.getCityTown() != null &&  !address.getCityTown().isBlank()) {
                cv.getAddress().setCityTown(address.getCityTown());
            }

            if (address.getZipCode() != null &&   !address.getZipCode().isBlank()) {
                cv.getAddress().setZipCode(address.getZipCode());
            }

        }

        if (cvUpdateDto.getAboutMe() != null && !cvUpdateDto.getAboutMe().isBlank()) {
            cv.setAboutMe(cvUpdateDto.getAboutMe());
        }

        if (cvUpdateDto.getContactInfo() != null) {

            if (cv.getContactInfo() == null) {
                cv.setContactInfo(new ContactInfo());
            }

            ContactInfo contactInfo = cvUpdateDto.getContactInfo();

            if(contactInfo.getContactEmail() != null && !contactInfo.getContactEmail().isBlank()){
                cv.getContactInfo().setContactEmail(contactInfo.getContactEmail());
            }
            if(contactInfo.getContactPhoneNumber() != null && !contactInfo.getContactPhoneNumber().isBlank()){
                cv.getContactInfo().setContactPhoneNumber(contactInfo.getContactPhoneNumber());
            }
            if (contactInfo.getFacebookLink() != null && !contactInfo.getFacebookLink().isBlank()) {
                cv.getContactInfo().setFacebookLink(contactInfo.getFacebookLink());
            }
            if (contactInfo.getTwitterLink() != null && !contactInfo.getTwitterLink().isBlank()) {
                cv.getContactInfo().setTwitterLink(contactInfo.getTwitterLink());
            }
            if (contactInfo.getLeetcodeLink() != null && !contactInfo.getLeetcodeLink().isBlank()) {
                cv.getContactInfo().setLeetcodeLink(contactInfo.getLeetcodeLink());
            }
            if (contactInfo.getLinkedinLink() != null && !contactInfo.getLinkedinLink().isBlank()) {
                cv.getContactInfo().setLinkedinLink(contactInfo.getLinkedinLink());
            }
            if (contactInfo.getHeadHunterLink() != null && !contactInfo.getHeadHunterLink().isBlank()) {
                cv.getContactInfo().setHeadHunterLink(contactInfo.getHeadHunterLink());
            }
            if (contactInfo.getInstagramUsername() != null && !contactInfo.getInstagramUsername().isBlank()) {
                cv.getContactInfo().setInstagramUsername(contactInfo.getInstagramUsername());
            }
            if (contactInfo.getTelegramUsername() != null && !contactInfo.getTelegramUsername().isBlank()) {
                cv.getContactInfo().setTelegramUsername(contactInfo.getTelegramUsername());
            }

        }

        if (cvUpdateDto.getDriverLicense() != null && !cvUpdateDto.getDriverLicense().isBlank()) {
            cv.setDriverLicense(cvUpdateDto.getDriverLicense());
        }

        if (cvUpdateDto.getGender() != null) {
            cv.setGender(cvUpdateDto.getGender());
        }

        return cv;

    }

    public List<CVResponseDto> toListResponseDto(List<CV> cvs, User user) {

        List<CVResponseDto> cvResponseDtos = new ArrayList<>();
        for (CV cv : cvs) {
            CVResponseDto cvResponseDto = toResponseDto(cv, user);
            cvResponseDtos.add(cvResponseDto);
        }
        return cvResponseDtos;


    }
}
