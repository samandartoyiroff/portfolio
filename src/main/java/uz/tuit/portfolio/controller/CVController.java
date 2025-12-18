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
import uz.tuit.portfolio.util.SecurityUtil;

import java.util.List;

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
            @RequestPart(name = "cvImage") MultipartFile cvImage
    ){

        User user = SecurityUtil.gerCurrentUser();
        if (user != null) {
            return cvService.createCV(cvCreateDto, cvImage, user);
        }
        else {
            return cvService.createCvNonUser(cvCreateDto, cvImage);
        }

    }

    @GetMapping("/myCVs")
    public ResponseEntity<List<CVResponseDto>> getAllCV(
            @AuthenticationPrincipal User user
            ){
        return cvService.getMyAllCv(user);
    }

    @PostMapping("/update/{cvId}")
    public ResponseEntity<?> updateProject(
            @RequestPart CVUpdateDto cvUpdateDto,
            @PathVariable Long cvId,
            @RequestPart(name = "cvImage", required = false) MultipartFile cvImage
            ){

        User user = SecurityUtil.gerCurrentUser();

        if (user != null) {
            return cvService.update(cvUpdateDto, user,cvImage, cvId);
        }
        else {
            return cvService.updateCVNonUser(cvUpdateDto, cvImage, cvId);
        }

    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CVResponseDto> findById(
            @PathVariable Long id
    ){
        return cvService.findById(id);
    }




    // Experience
    @PostMapping("/experience/add/{cvId}")
    public ResponseEntity<ExperienceResponseDto> toResponseDto(
            @RequestBody ExperienceCreateDto experienceCreateDto,
            @PathVariable Long cvId
    ) {

        User user = SecurityUtil.gerCurrentUser();

        return experienceService.addExperience(experienceCreateDto, user, cvId);
    }

    @PostMapping("/experience/update/{cvId}/{id}")
    public ResponseEntity<ExperienceResponseDto> updateExperience(
            @PathVariable(name = "id") Long id,
            @RequestBody ExperienceUpdateDto experienceUpdateDto,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();

        return experienceService.updateExperience(id, experienceUpdateDto, cvId, user);
    }

    @PostMapping("/experience/delete/{cvId}/{id}")
    public ResponseEntity<?> deleteExperience(
            @PathVariable(name = "id") Long id,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();

        return experienceService.delete(id, user, cvId);
    }

    //Hard Skill

    @PostMapping("/hard-skill/add/{cvId}")
    public ResponseEntity<HardSkillResponseDto> addHardSkill(
            @RequestBody  HardSkillCreateDto hardSkillCreateDto,
            @PathVariable Long cvId

    ){

        User user = SecurityUtil.gerCurrentUser();

        return hardSkillService.addHardSkill(hardSkillCreateDto, user, cvId);
    }

    @PostMapping("/hard-skill/remove/{cvId}/{id}")
    public ResponseEntity<?> removeHardSkill(
            @RequestParam(name = "id")  Long id,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return hardSkillService.removeHardSkill(user, id, cvId);
    }

    //Soft Skill

    @PostMapping("/soft-skill/add/{cvId}")
    public ResponseEntity<SoftSkillResponseDto> addSoftSkill(
            @RequestBody SoftSkillCreateDto softSkillCreateDto,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();

        return softSkillService.addSoftSkill(user, softSkillCreateDto, cvId);
    }

    @PostMapping("/soft-skill/remove/{cvId}/{softSkillId}")
    public ResponseEntity<?> removeSoftSkill(
            @PathVariable Long cvId,
            @PathVariable Long softSkillId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return softSkillService.removeSoftSkill(softSkillId, user,cvId);
    }

    // Education

    @PostMapping("/education/add/{cvId}")
    public ResponseEntity<EducationResponseDto> addEducation(

            @RequestBody @Valid EducationCreateDto educationCreateDto,
            @PathVariable Long cvId

    ){
        User user = SecurityUtil.gerCurrentUser();
        return educationService.addEducation(user, educationCreateDto, null, cvId);
    }

    @PostMapping("/education/update/{cvId}/{id}")
    public ResponseEntity<EducationResponseDto> updateEducation(
            @PathVariable(name = "id") Long id,
            @RequestBody(required = false) EducationUpdateDto educationUpdateDto,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return educationService.update(educationUpdateDto, id, user, null, cvId);
    }

    @PostMapping("/education/remove/{cvId}/{id}")
    public ResponseEntity<?> removeEducation(
            @PathVariable(name = "id") Long id,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return educationService.removeEducation(id, user, cvId);
    }

    // Certificate

    @PostMapping("/certificate/add/{cvId}")
    public ResponseEntity<CertificateResponseDto> addCertificate(
            @RequestBody @Valid CertificateCreateDto certificateCreateDto,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return certificateService.addCertificate(user, certificateCreateDto, null, cvId);
    }

    @PostMapping("/certificate/update/{cvId}/{id}")
    public ResponseEntity<CertificateResponseDto> updateCertificate(
            @RequestBody(required = false) CertificateUpdateDto certificateUpdateDto,
            @PathVariable Long id,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return certificateService.updateCertificate(certificateUpdateDto, user, id, null, cvId);
    }

    @PostMapping("/certificate/remove/{cvId}/{id}")
    public ResponseEntity<?> removeCertificate(
            @PathVariable Long id,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return certificateService.removeCertificate(id,user,cvId);
    }

    // Language Skill

    @PostMapping("/language-skill/add/{cvId}")
    public ResponseEntity<?> addLanguageSkill(
            @RequestBody @Valid LanguageSkillCreateDto languageSkillCreateDto,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return languageSkillService.addLanguageSkill(languageSkillCreateDto, user, cvId );
    }

    @PostMapping("/language-skill/update/{cvId}/{id}")
    public ResponseEntity<?> updateLanguageSkill(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid LanguageSkillUpdateDto languageSkillUpdateDto,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return languageSkillService.update(id, languageSkillUpdateDto, user, cvId);
    }

    @PostMapping("/language-skill/remove/{cvId}/{id}")
    public ResponseEntity<?> removeLanguageSkill(
            @PathVariable(name = "id") Long id,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return languageSkillService.removeLanguageSkill(id, user, cvId);
    }

    // Project
    @PostMapping("/project/add/{cvId}")
    public ResponseEntity<?> addProject(
            @RequestBody @Valid ProjectCreateDto projectCreateDto,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return projectService.addProject(user, projectCreateDto, cvId);
    }

    @PostMapping("/project/update/{cvId}/{id}")
    public ResponseEntity<?> updateProject(
            @RequestBody @Valid ProjectUpdateDto projectUpdateDto,
            @PathVariable(name = "id") Long id,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return projectService.updateProject(id,user, projectUpdateDto, cvId);
    }

    @PostMapping("/project/delete/{cvId}/{id}")
    public ResponseEntity<?> deleteProject(
            @PathVariable(name = "id") Long id,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return projectService.deleteProject(id, user, cvId);
    }



    // Hobby
    @PostMapping("/hobby/add/{cvId}")
    public ResponseEntity<?> addHobby(
            @RequestParam(name = "hobby") String hobby,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return cvService.addHobby(hobby, user,cvId);
    }

    @PostMapping("/hobby/remove/{cvId}")
    public ResponseEntity<?> removeHobby(
            @RequestParam(name = "hobby") String hobby,
            @PathVariable Long cvId
    ){
        User user = SecurityUtil.gerCurrentUser();
        return cvService.removeHobby(hobby, user, cvId);
    }

    @PostMapping("/delete-cv/{cvId}")
    public ResponseEntity<?> deleteCv(
            @PathVariable Long cvId,
            @AuthenticationPrincipal User user
    ){
        return cvService.deleteCv(cvId, user);
    }

}