package uz.tuit.portfolio.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.Education;
import uz.tuit.portfolio.domain.File;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.EducationCreateDto;
import uz.tuit.portfolio.dto.request.EducationUpdateDto;
import uz.tuit.portfolio.dto.response.EducationResponseDto;
import uz.tuit.portfolio.mapper.EducationMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.EducationRepository;
import uz.tuit.portfolio.service.EducationService;
import uz.tuit.portfolio.service.FileService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {


    private final EducationMapper educationMapper;
    private final CVRepository cVRepository;
    private final EducationRepository educationRepository;
    private final FileService fileService;

    @Override
    @Transactional
    public ResponseEntity<EducationResponseDto> addEducation(User user, EducationCreateDto educationCreateDto, MultipartFile educationFile) {

        Education education = educationMapper.toEntity(educationCreateDto);

        if (educationFile != null) {
            File file = fileService.uploadFile(educationFile);
            education.setCertificate(file);
        }

        CV cv = user.getCv();
        education.setCv(cv);
        List<Education> educations = cv.getEducations();
        educations.add(education);
        cv.setEducations(educations);
        cVRepository.save(cv);

        return ResponseEntity.ok(educationMapper.educationToEducationDto(education));


    }

    @Override
    @Transactional
    public ResponseEntity<EducationResponseDto> update(EducationUpdateDto educationUpdateDto, Long id, User user, MultipartFile educationFile) {

        CV cv = user.getCv();
        List<Education> educations = cv.getEducations();

        Education education = educations.stream().filter(education1 -> education1.getId().equals(id)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Education with id: " + id + " not found"));


        if (educationFile != null) {
            File file = fileService.updateFile(educationFile, education.getCertificate());
            education.setCertificate(file);
        }

        if (educationUpdateDto != null) {
            education = educationMapper.update(educationUpdateDto, education);
        }


        educationRepository.save(education);

        return ResponseEntity.ok(educationMapper.educationToEducationDto(education));


    }

    @Override
    @Transactional
    public ResponseEntity<?> removeEducation(Long id, User user) {

        CV cv = user.getCv();

        List<Education> educations = cv.getEducations();

        Education education1 = educations.stream()
                .filter(education -> education.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Education with id: " + id + " not found"));

        File certificate = education1.getCertificate();

        educations.remove(education1);

        cVRepository.save(cv);

        fileService.deleteFile(certificate);

        return ResponseEntity.ok("Education with id: " + id + " has been removed");

    }
}
