package uz.tuit.portfolio.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.*;
import uz.tuit.portfolio.dto.response.*;
import uz.tuit.portfolio.service.*;

@RestController
@RequestMapping("/api/v1/cv")
@RequiredArgsConstructor
public class CVController {

    private final CVService cvService;

    private final ExperienceService experienceService;
    private final HardSkillService hardSkillService;
    private final SoftSkillService softSkillService;
    private final EducationService educationService;
    private final TechnologyService technologyService;
    private final CertificateService certificateService;
    private final LanguageSkillService languageSkillService;
    private final ProjectService projectService;

    // CV

    @PostMapping( "/create")
    public ResponseEntity<CVResponseDto> createCv(
            @RequestPart @Valid CVCreateDto cvCreateDto,
            @RequestPart(name = "cvImage") MultipartFile cvImage,
            @AuthenticationPrincipal User user
    ){

        return cvService.createCV(cvCreateDto, cvImage, user);
    }

    @GetMapping("/myCV")
    public ResponseEntity<CVResponseDto> getCV(
            @AuthenticationPrincipal User user
            ){
        return cvService.getMyCv(user);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProject(
            @RequestPart CVUpdateDto cvUpdateDto,
            @RequestPart(required = false) MultipartFile profilePhoto,
            @AuthenticationPrincipal User user
    ){
        return cvService.update(cvUpdateDto, user, profilePhoto);
    }

    // Experience

    @PostMapping("/experience/add")
    public ResponseEntity<ExperienceResponseDto> toResponseDto(
            @RequestBody ExperienceCreateDto experienceCreateDto,
            @AuthenticationPrincipal User user
    ) {
        return experienceService.addExperience(experienceCreateDto, user);
    }

    @PostMapping("/experience/update/{id}")
    public ResponseEntity<ExperienceResponseDto> updateExperience(
            @PathVariable(name = "id") Long id,
            @RequestBody ExperienceUpdateDto experienceUpdateDto
    ){
        return experienceService.updateExperience(id, experienceUpdateDto);
    }

    @PostMapping("/experience/delete/{id}")
    public ResponseEntity<?> deleteExperience(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user
    ){
        return experienceService.delete(id, user);
    }

    //Hard Skill

    @PostMapping("/hard-skill/add")
    public ResponseEntity<HardSkillResponseDto> addHardSkill(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "hardSkillId")  Long hardSkillId

    ){
        return hardSkillService.addHardSkill(hardSkillId, user);
    }

    @PostMapping("/hard-skill/remove/{id}")
    public ResponseEntity<?> removeHardSkill(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "id")  Long id
    ){
        return hardSkillService.removeHardSkill(user, id);
    }

    //Soft Skill

    @PostMapping("/soft-skill/add")
    public ResponseEntity<SoftSkillResponseDto> addSoftSkill(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "softSkillId") Long softSkillId
    ){
        return softSkillService.addSoftSkill(user, softSkillId);
    }

    @PostMapping("/soft-skill/remove/{id}")
    public ResponseEntity<?> removeSoftSkill(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user
    ){
        return softSkillService.removeSoftSkill(id, user);
    }

    // Education

    @PostMapping("/education/add")
    public ResponseEntity<EducationResponseDto> addEducation(

            @RequestPart @Valid EducationCreateDto educationCreateDto,
            @AuthenticationPrincipal User user,
            @RequestPart(required = false) MultipartFile educationFile

    ){
        return educationService.addEducation(user, educationCreateDto, educationFile);
    }

    @PostMapping("/education/update/{id}")
    public ResponseEntity<EducationResponseDto> updateEducation(
            @PathVariable(name = "id") Long id,
            @RequestPart(required = false) EducationUpdateDto educationUpdateDto,
            @RequestPart(required = false) MultipartFile educationFile,
            @AuthenticationPrincipal User user
    ){
        return educationService.update(educationUpdateDto, id, user, educationFile);
    }

    @PostMapping("/education/remove/{id}")
    public ResponseEntity<?> removeEducation(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user
    ){
        return educationService.removeEducation(id, user);
    }

    // Technology

    @PostMapping("/technology/add")
    public ResponseEntity<TechnologyResponseDto> addTechnology(

            @RequestParam(name = "technologyId") Long technologyId,
            @AuthenticationPrincipal User user
    ){
        return technologyService.addTechnology(technologyId, user);
    }

    @PostMapping("/technology/remove/{id}")
    public ResponseEntity<?> removeTechnology(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user
    ){
        return technologyService.removeTechnology(id, user);
    }

    // Certificate

    @PostMapping("/certificate/add")
    public ResponseEntity<CertificateResponseDto> addCertificate(
            @RequestPart @Valid CertificateCreateDto certificateCreateDto,
            @AuthenticationPrincipal User user,
            @RequestPart(required = false) MultipartFile certificateFile
    ){
        return certificateService.addCertificate(user, certificateCreateDto, certificateFile);
    }

    @PostMapping("/certificate/update/{id}")
    public ResponseEntity<CertificateResponseDto> updateCertificate(
            @RequestPart(required = false) CertificateUpdateDto certificateUpdateDto,
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestPart(required = false) MultipartFile certificateFile
    ){
        return certificateService.updateCertificate(certificateUpdateDto, user, id, certificateFile);
    }

    @PostMapping("/certificate/remove/{id}")
    public ResponseEntity<?> removeCertificate(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ){
        return certificateService.removeCertificate(id,user);
    }

    // Language Skill

    @PostMapping("/language-skill/add")
    public ResponseEntity<?> addLanguageSkill(
            @RequestBody @Valid LanguageSkillCreateDto languageSkillCreateDto,
            @AuthenticationPrincipal User user
    ){
        return languageSkillService.addLanguageSkill(languageSkillCreateDto, user);
    }

    @PostMapping("/language-skill/update/{id}")
    public ResponseEntity<?> updateLanguageSkill(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid LanguageSkillUpdateDto languageSkillUpdateDto,
            @AuthenticationPrincipal User user
    ){
        return languageSkillService.update(id, languageSkillUpdateDto, user);
    }

    @PostMapping("/language-skill/remove/{id}")
    public ResponseEntity<?> removeLanguageSkill(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user
    ){
        return languageSkillService.removeLanguageSkill(id, user);
    }

    // Project

    @PostMapping("/project/add")
    public ResponseEntity<?> addProject(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid ProjectCreateDto projectCreateDto
    ){
        System.out.println("ProjectMapper.addProject");
        return projectService.addProject(user, projectCreateDto);
    }

    @PostMapping("/project/update/{id}")
    public ResponseEntity<?> updateProject(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid ProjectUpdateDto projectUpdateDto,
            @PathVariable(name = "id") Long id
    ){
        return projectService.updateProject(id,user, projectUpdateDto);
    }

    @PostMapping("/project/delete/{id}")
    public ResponseEntity<?> deleteProject(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "id") Long id
    ){
        return projectService.deleteProject(id, user);
    }



    // Hobby

    @PostMapping("/hobby/add")
    public ResponseEntity<?> addHobby(
            @RequestParam(name = "hobby") String hobby,
            @AuthenticationPrincipal User user
    ){
        return cvService.addHobby(hobby, user);
    }

    @PostMapping("/hobby/remove")
    public ResponseEntity<?> removeHobby(
            @RequestParam(name = "hobby") String hobby,
            @AuthenticationPrincipal User user
    ){
        return cvService.removeHobby(hobby, user);
    }

}