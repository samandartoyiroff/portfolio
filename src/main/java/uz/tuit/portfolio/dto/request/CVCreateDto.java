package uz.tuit.portfolio.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.tuit.portfolio.domain.Address;
import uz.tuit.portfolio.domain.ContactInfo;
import uz.tuit.portfolio.dto.response.*;
import uz.tuit.portfolio.model.Gender;

import java.util.List;

@Setter
@Getter
@ToString
public class CVCreateDto {

    private String occupationName;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String aboutMe;

    private Gender gender;

    private ContactInfo contactInfo;

    private Address address;

    private List<Long> softSkillIds;

    private List<Long> hardSkillIds;

    private List<Long> technologySkillIds;

    private List<String> hobbies;

    private List<ProjectCreateDto> projects;

    private List<LanguageSkillCreateDto> languages;

    private List<ExperienceCreateDto>  experience;

    private List<EducationCreateDto> educations;

    private List<CertificateCreateDto> certificates;

    private String driverLicense;

}
