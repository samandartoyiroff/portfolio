package uz.tuit.portfolio.service;

import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.Image;


public interface ImageService {

    Image uploadImage(MultipartFile imageFile);

    Image updateImage(MultipartFile imageFile, Image image);

    void deleteImage(Image image);
}
