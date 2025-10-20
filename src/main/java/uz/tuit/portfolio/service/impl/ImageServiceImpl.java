package uz.tuit.portfolio.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.Image;
import uz.tuit.portfolio.repository.ImageRepository;
import uz.tuit.portfolio.service.ImageService;
import uz.tuit.portfolio.util.ScriptImageCheckUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ScriptImageCheckUtil imageCheckIUtil;
    private final ImageRepository imageRepository;

    @Value("${file.upload.directory.image}")
    private String uploadDirectory;

    @Override
    @Transactional
    public Image uploadImage(MultipartFile imageFile) {
        try {
            // Tekshiruv
            imageCheckIUtil.validateFile(imageFile);

            String originalFilename = Objects.requireNonNull(imageFile.getOriginalFilename());
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID() + "." + extension;

            Path targetPath = Path.of(uploadDirectory, fileName);

            // Asinxron saqlash
            CompletableFuture.runAsync(() -> {
                try {
                    Files.copy(imageFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
                }
            }).exceptionally(ex -> {
                throw new RuntimeException("Failed to save image: " + ex.getMessage(), ex);
            });

            Image image = new Image();
            image.setContentType(imageFile.getContentType());
            image.setName(fileName);
            image.setSize(imageFile.getSize());
            image.setPath(targetPath.toString());
            image.setExtension(extension);

            return imageRepository.save(image);

        } catch (Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Image updateImage(MultipartFile imageFile, Image oldImage) {
        try {
            if (oldImage != null) {
                Files.deleteIfExists(Path.of(oldImage.getPath()));
            }
            return uploadImage(imageFile);
        } catch (Exception e) {
            throw new RuntimeException("Update failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteImage(Image image) {

        try {

            String path = image.getPath();

            Files.deleteIfExists(Path.of(path));

        }
        catch (Exception e) {
            throw new RuntimeException("Delete failed: " + e.getMessage(), e);
        }

    }
}
