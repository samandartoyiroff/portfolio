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
            @RequestPart(name = "cvImage") MultipartFile cvImage,
            @AuthenticationPrincipal User user
    ){

        return cvService.createCV(cvCreateDto, cvImage, user);
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
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId,
            @RequestPart(name = "cvImage", required = false) MultipartFile cvImage

            ){
        return cvService.update(cvUpdateDto, user,cvImage, cvId);
    }

    // Experience

    @PostMapping("/experience/add/{cvId}")
    public ResponseEntity<ExperienceResponseDto> toResponseDto(
            @RequestBody ExperienceCreateDto experienceCreateDto,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ) {
        return experienceService.addExperience(experienceCreateDto, user, cvId);
    }

    @PostMapping("/experience/update/{cvId}/{id}")
    public ResponseEntity<ExperienceResponseDto> updateExperience(
            @PathVariable(name = "id") Long id,
            @RequestBody ExperienceUpdateDto experienceUpdateDto,
            @PathVariable Long cvId,
            @AuthenticationPrincipal User user
    ){
        return experienceService.updateExperience(id, experienceUpdateDto, cvId, user);
    }

    @PostMapping("/experience/delete/{cvId}/{id}")
    public ResponseEntity<?> deleteExperience(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return experienceService.delete(id, user, cvId);
    }

    //Hard Skill

    @PostMapping("/hard-skill/add/{cvId}")
    public ResponseEntity<HardSkillResponseDto> addHardSkill(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "hardSkillId")  Long hardSkillId,
            @PathVariable Long cvId

    ){
        return hardSkillService.addHardSkill(hardSkillId, user, cvId);
    }

    @PostMapping("/hard-skill/remove/{cvId}/{id}")
    public ResponseEntity<?> removeHardSkill(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "id")  Long id,
            @PathVariable Long cvId
    ){
        return hardSkillService.removeHardSkill(user, id, cvId);
    }

    //Soft Skill

    @PostMapping("/soft-skill/add/{cvId}")
    public ResponseEntity<SoftSkillResponseDto> addSoftSkill(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "softSkillId") Long softSkillId,
            @PathVariable Long cvId
    ){
        return softSkillService.addSoftSkill(user, softSkillId, cvId);
    }

    @PostMapping("/soft-skill/remove/{cvId}/{id}")
    public ResponseEntity<?> removeSoftSkill(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return softSkillService.removeSoftSkill(id, user,cvId);
    }

    // Education

    @PostMapping("/education/add/{cvId}")
    public ResponseEntity<EducationResponseDto> addEducation(

            @RequestBody @Valid EducationCreateDto educationCreateDto,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId

    ){
        return educationService.addEducation(user, educationCreateDto, null, cvId);
    }

    @PostMapping("/education/update/{cvId}/{id}")
    public ResponseEntity<EducationResponseDto> updateEducation(
            @PathVariable(name = "id") Long id,
            @RequestBody(required = false) EducationUpdateDto educationUpdateDto,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return educationService.update(educationUpdateDto, id, user, null, cvId);
    }

    @PostMapping("/education/remove/{cvId}/{id}")
    public ResponseEntity<?> removeEducation(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return educationService.removeEducation(id, user, cvId);
    }

    // Technology

    @PostMapping("/technology/add/{cvId}")
    public ResponseEntity<TechnologyResponseDto> addTechnology(

            @RequestParam(name = "technologyId") Long technologyId,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return technologyService.addTechnology(technologyId, user, cvId);
    }

    @PostMapping("/technology/remove/{cvId}/{id}")
    public ResponseEntity<?> removeTechnology(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return technologyService.removeTechnology(id, user, cvId);
    }

    // Certificate

    @PostMapping("/certificate/add/{cvId}")
    public ResponseEntity<CertificateResponseDto> addCertificate(
            @RequestBody @Valid CertificateCreateDto certificateCreateDto,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return certificateService.addCertificate(user, certificateCreateDto, null, cvId);
    }

    @PostMapping("/certificate/update/{cvId}/{id}")
    public ResponseEntity<CertificateResponseDto> updateCertificate(
            @RequestBody(required = false) CertificateUpdateDto certificateUpdateDto,
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @PathVariable Long cvId
    ){
        return certificateService.updateCertificate(certificateUpdateDto, user, id, null, cvId);
    }

    @PostMapping("/certificate/remove/{cvId}/{id}")
    public ResponseEntity<?> removeCertificate(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return certificateService.removeCertificate(id,user,cvId);
    }

    // Language Skill

    @PostMapping("/language-skill/add/{cvId}")
    public ResponseEntity<?> addLanguageSkill(
            @RequestBody @Valid LanguageSkillCreateDto languageSkillCreateDto,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return languageSkillService.addLanguageSkill(languageSkillCreateDto, user, cvId );
    }

    @PostMapping("/language-skill/update/{cvId}/{id}")
    public ResponseEntity<?> updateLanguageSkill(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid LanguageSkillUpdateDto languageSkillUpdateDto,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return languageSkillService.update(id, languageSkillUpdateDto, user, cvId);
    }

    @PostMapping("/language-skill/remove/{cvId}/{id}")
    public ResponseEntity<?> removeLanguageSkill(
            @PathVariable(name = "id") Long id,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return languageSkillService.removeLanguageSkill(id, user, cvId);
    }

    // Project

    @PostMapping("/project/add/{cvId}")
    public ResponseEntity<?> addProject(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid ProjectCreateDto projectCreateDto,
            @PathVariable Long cvId
    ){
        System.out.println("ProjectMapper.addProject");
        return projectService.addProject(user, projectCreateDto, cvId);
    }

    @PostMapping("/project/update/{cvId}/{id}")
    public ResponseEntity<?> updateProject(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid ProjectUpdateDto projectUpdateDto,
            @PathVariable(name = "id") Long id,
            @PathVariable Long cvId
    ){
        return projectService.updateProject(id,user, projectUpdateDto, cvId);
    }

    @PostMapping("/project/delete/{cvId}/{id}")
    public ResponseEntity<?> deleteProject(
            @AuthenticationPrincipal User user,
            @PathVariable(name = "id") Long id,
            @PathVariable Long cvId
    ){
        return projectService.deleteProject(id, user, cvId);
    }



    // Hobby
    @PostMapping("/hobby/add/{cvId}")
    public ResponseEntity<?> addHobby(
            @RequestParam(name = "hobby") String hobby,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return cvService.addHobby(hobby, user,cvId);
    }

    @PostMapping("/hobby/remove/{cvId}")
    public ResponseEntity<?> removeHobby(
            @RequestParam(name = "hobby") String hobby,
            @AuthenticationPrincipal User user,
            @PathVariable Long cvId
    ){
        return cvService.removeHobby(hobby, user, cvId);
    }

}