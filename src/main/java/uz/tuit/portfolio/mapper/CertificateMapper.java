package uz.tuit.portfolio.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.tuit.portfolio.domain.Certificate;
import uz.tuit.portfolio.domain.File;
import uz.tuit.portfolio.dto.request.CertificateCreateDto;
import uz.tuit.portfolio.dto.request.CertificateUpdateDto;
import uz.tuit.portfolio.dto.response.CertificateResponseDto;
import uz.tuit.portfolio.service.FileService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CertificateMapper {


    private final FileService fileService;

    public CertificateResponseDto toResponseDto(Certificate certificate) {

        CertificateResponseDto certificateResponseDto = new CertificateResponseDto();
        certificateResponseDto.setId(certificate.getId());
        certificateResponseDto.setName(certificate.getName());
        certificateResponseDto.setDescription(certificate.getDescription());

        if (certificate.getFile()!=null) {

            File file = certificate.getFile();
            certificateResponseDto.setCertificateFileId(file.getId());
            certificateResponseDto.setCertificateFilePath(file.getPath());

        }

        return certificateResponseDto;

    }

    public Certificate toEntity(CertificateCreateDto certificateCreateDto) {

        Certificate certificate = new Certificate();
        certificate.setName(certificateCreateDto.getName());
        certificate.setDescription(certificateCreateDto.getDescription());

       /* File file = fileService.uploadFile(certificateCreateDto.getFile());

        certificate.setFile(file);*/
        return certificate;

    }

    public List<Certificate> toListEntity(List<CertificateCreateDto> certificates) {

        return certificates.stream().map(this::toEntity).collect(Collectors.toList());

    }

    public List<CertificateResponseDto> toListResponse(List<Certificate> certificates) {

        return certificates.stream().map(this::toResponseDto).collect(Collectors.toList());

    }

    public Certificate updateCertificate(CertificateUpdateDto certificateUpdateDto, Certificate certificate1) {

        if(certificateUpdateDto.getName()!=null && !certificateUpdateDto.getName().isBlank()) {
            certificate1.setName(certificateUpdateDto.getName());
        }
        certificate1.setDescription(certificateUpdateDto.getDescription());

        return certificate1;

    }
}
