package uz.tuit.portfolio.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.File;


public interface FileService {

    File uploadFile(MultipartFile multipartFile);

    File updateFile(MultipartFile multipartFile, File oldFile);


    ResponseEntity<?> downloadFile(Long id);

    void deleteFile(File file);

}
