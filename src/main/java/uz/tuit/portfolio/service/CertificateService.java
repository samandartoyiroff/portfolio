package uz.tuit.portfolio.service;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.User;
import uz.tuit.portfolio.dto.request.CertificateCreateDto;
import uz.tuit.portfolio.dto.request.CertificateUpdateDto;
import uz.tuit.portfolio.dto.response.CertificateResponseDto;

public interface CertificateService {
    ResponseEntity<CertificateResponseDto> addCertificate(User user, @Valid CertificateCreateDto certificateCreateDto, MultipartFile certificateFile, Long cvId);

    ResponseEntity<CertificateResponseDto> updateCertificate(@Valid CertificateUpdateDto certificateUpdateDto, User user, Long id, MultipartFile certificateFile, Long cvId);

    ResponseEntity<?> removeCertificate(Long id, User user, Long cvId);

    ResponseEntity<CertificateResponseDto> addCertificateToPortfolio(User user, @Valid CertificateCreateDto certificateCreateDto, MultipartFile certificateFile);

    ResponseEntity<CertificateResponseDto> updateCertificateInPortfolio(CertificateUpdateDto certificateUpdateDto, User user, Long id, MultipartFile certificateFile);

    ResponseEntity<?> removeCertificateFromPortfolio(Long id, User user);
}
