package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.CV;
import uz.tuit.portfolio.domain.Certificate;
import uz.tuit.portfolio.domain.File;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.CertificateCreateDto;
import uz.tuit.portfolio.dto.request.CertificateUpdateDto;
import uz.tuit.portfolio.dto.response.CertificateResponseDto;
import uz.tuit.portfolio.mapper.CertificateMapper;
import uz.tuit.portfolio.repository.CVRepository;
import uz.tuit.portfolio.repository.CertificateRepository;
import uz.tuit.portfolio.service.CertificateService;
import uz.tuit.portfolio.service.FileService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {


    private final CertificateMapper certificateMapper;
    private final CVRepository cVRepository;
    private final CertificateRepository certificateRepository;
    private final FileService fileService;

    @Override
    @Transactional
    public ResponseEntity<CertificateResponseDto> addCertificate(User user, CertificateCreateDto certificateCreateDto, MultipartFile certificateFile) {

        Certificate certificate = certificateMapper.toEntity(certificateCreateDto);

        if (certificateFile != null) {
            File file = fileService.uploadFile(certificateFile);
            certificate.setFile(file);
        }

        CV cv = user.getCv();

        certificate.setCv(cv);

        certificateRepository.save(certificate);

        return ResponseEntity.ok().body(certificateMapper.toResponseDto(certificate));


    }

    @Override
    @Transactional
    public ResponseEntity<CertificateResponseDto> updateCertificate(CertificateUpdateDto certificateUpdateDto, User user, Long id, MultipartFile certificateFile) {

        CV cv = user.getCv();

        List<Certificate> certificates = cv.getCertificates();

        Certificate certificate1 = certificates.stream().filter(certificate -> certificate.getId().equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException("Certificate not belong this user"));

        if(certificateUpdateDto != null) {
            certificate1 = certificateMapper.updateCertificate(certificateUpdateDto, certificate1);
        }

        if (certificateFile != null) {
            File file = fileService.updateFile(certificateFile, certificate1.getFile());
            certificate1.setFile(file);
        }

        certificateRepository.save(certificate1);

        return ResponseEntity.ok().body(certificateMapper.toResponseDto(certificate1));

    }

    @Override
    @Transactional
    public ResponseEntity<?> removeCertificate(Long id, User user) {

        CV cv = user.getCv();

        List<Certificate> certificates = cv.getCertificates();

        Certificate certificate1 = certificates.stream().filter(certificate -> certificate.getId().equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException("Certificate not belong this user"));

        File file = certificate1.getFile();

        certificates.remove(certificate1);

        cv.setCertificates(certificates);

        certificate1.setCv(null); // <--- bu qo‘shimcha, lekin juda muhim

        cVRepository.save(cv);

        if (file != null) {
            fileService.deleteFile(file);
        }

        return ResponseEntity.ok().body("Certificate has been removed");


    }
}
