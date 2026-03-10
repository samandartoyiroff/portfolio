package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.*;
import uz.tuit.portfolio.dto.request.*;
import uz.tuit.portfolio.dto.response.CVResponseDto;
import uz.tuit.portfolio.repository.CVRepository;
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


    public CVResponseDto toResponseDto(CV cv, User user) {

        CVResponseDto cvResponseDto = new CVResponseDto();

        cvResponseDto.setId(cv.getId());

        if (user!=null){
            cvResponseDto.setUserId(user.getId());
        }

        cvResponseDto.setGender(cv.getGender());

        cvResponseDto.setFullName(cv.getFullName());

        cvResponseDto.setEmail(cv.getEmail());

        cvResponseDto.setPhoneNumber( cv.getPhoneNumber());

        cvResponseDto.setAboutMe( cv.getAboutMe());

        cvResponseDto.setAddress( cv.getAddress());

        cvResponseDto.setContactInfo(cv.getContactInfo());

        cvResponseDto.setHobbies(cv.getHobbies());

        cvResponseDto.setOccupation(cv.getOccupation());

        cvResponseDto.setDateOfBirth(cv.getBirthDate());

        cvResponseDto.setCvImage(
                cVImageMapper.toResponseDto(cv.getCvPhoto()!=null ? cv.getCvPhoto() : null)
        );


        List<SoftSkill> softSkills = cv.getSoftSkills();
        if (softSkills != null &&  !softSkills.isEmpty()) {
            cvResponseDto.setSoftSkills(
                    softSkillMapper.toListResponseDto(softSkills)
            );
        }


        List<HardSkill> hardSkills = cv.getHardSkills();
        if (hardSkills != null && !hardSkills.isEmpty()) {
            cvResponseDto.setHardSkills(
                    hardSkillMapper.toListResponse(hardSkills)
            );
        }


        List<Project> projects = cv.getProjects();
        if (projects != null && !projects.isEmpty()) {
            cvResponseDto.setProjects(
                    projectMapper.toListDto(projects)
            );
        }

        List<LanguageSkill> languageSkills = cv.getLanguageSkills();
        if (languageSkills != null && !languageSkills.isEmpty()) {
            cvResponseDto.setLanguages(
                    languageSkillMapper.toListResponse(languageSkills)
            );
        }

        List<Experience> experiences = cv.getExperiences();
        if (experiences != null && !experiences.isEmpty()) {
            cvResponseDto.setExperience(
                    experienceMapper.toListResponse(experiences)
            );
        }

        List<Education> educations = cv.getEducations();
        if (educations != null && !educations.isEmpty()) {
            cvResponseDto.setEducations(
                    educationMapper.toListResponse(educations)
            );
        }

        List<Certificate> certificates = cv.getCertificates();
        if (certificates != null && !certificates.isEmpty()) {
            cvResponseDto.setCertificates(
                    certificateMapper.toListResponse(certificates)
            );
        }

        cvResponseDto.setDriverLicense(cv.getDriverLicense());

        cvResponseDto.setTemplate(cv.getTemplate());

        return cvResponseDto;

    }

    public CV toEntity(CVCreateDto cvCreateDto, MultipartFile cvImage) {

        CV cv = new CV();

        cv.setFullName(cvCreateDto.getFullName());

        cv.setEmail(cvCreateDto.getEmail());

        cv.setPhoneNumber(cvCreateDto.getPhoneNumber());

        Image image = imageService.uploadImage(cvImage);

        cv.setCvPhoto(image);

        cv.setGender(cvCreateDto.getGender());

        cv.setAddress(cvCreateDto.getAddress());

        cv.setBirthDate(cvCreateDto.getDateOfBirth());

        cv.setAboutMe(cvCreateDto.getAboutMe());

        cv.setHobbies(cvCreateDto.getHobbies());

        cv.setContactInfo(cvCreateDto.getContactInfo());

        cv.setOccupation(cvCreateDto.getOccupation());

        List<ExperienceCreateDto> experience = cvCreateDto.getExperience();
        if (experience != null && !experience.isEmpty()) {
            List<Experience> experiences = experienceMapper.toListEntity(experience);
            cv.setExperiences(experiences);
        }


        List<Long> softSkillIds = cvCreateDto.getSoftSkillIds();
        if (softSkillIds != null && !softSkillIds.isEmpty()) {
            System.out.println("Soft skill null emas");
            List<SoftSkill> softSkills = softSkillMapper.toListEntity(softSkillIds);
            cv.setSoftSkills(softSkills);
        }


        List<Long> hardSkillIds = cvCreateDto.getHardSkillIds();
        if (hardSkillIds != null && !hardSkillIds.isEmpty()) {
            List<HardSkill> hardSkills = hardSkillMapper.toListEntity(hardSkillIds);
            cv.setHardSkills(hardSkills);
        }

        List<EducationCreateDto> educations1 = cvCreateDto.getEducations();
        if (educations1 != null && !educations1.isEmpty()) {
            List<Education> educations = educationMapper.toListEntity(educations1);
            cv.setEducations(educations);
        }

        List<CertificateCreateDto> certificates1 = cvCreateDto.getCertificates();
        if (certificates1 != null && !certificates1.isEmpty()) {
            List<Certificate> certificates = certificateMapper.toListEntity(certificates1);
            cv.setCertificates(certificates);
        }

        List<LanguageSkillCreateDto> languages = cvCreateDto.getLanguages();
        if (languages != null && !languages.isEmpty()) {
            List<LanguageSkill> languageSkills = languageSkillMapper.toListEntity(languages);
            cv.setLanguageSkills(languageSkills);
        }

        List<ProjectCreateDto> projects1 = cvCreateDto.getProjects();
        if (projects1 != null && !projects1.isEmpty()) {
            List<Project> projects = projectMapper.toListEntity(projects1);
            cv.setProjects(projects);
        }

        cv.setTemplate(cvCreateDto.getTemplate());

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

        if (cvUpdateDto.getTemplate() != null &&  !cvUpdateDto.getTemplate().isBlank()) {
            cv.setTemplate(cvUpdateDto.getTemplate());
        }

        if(cvUpdateDto.getDateOfBirth()!=null){
            cv.setBirthDate(cvUpdateDto.getDateOfBirth());
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
