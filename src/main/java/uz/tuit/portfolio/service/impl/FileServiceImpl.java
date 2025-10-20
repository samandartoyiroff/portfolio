package uz.tuit.portfolio.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.tuit.portfolio.domain.File;
import uz.tuit.portfolio.repository.FileRepository;
import uz.tuit.portfolio.service.FileService;
import uz.tuit.portfolio.util.ScriptFileCheckUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {


    private final FileRepository fileRepository;

    private final ScriptFileCheckUtil fileCheckUtil;

    @Value("${file.upload.directory.file}")
    private String uploadDirectory;

    @Override
    public File uploadFile(MultipartFile multipartFile) {

        try {
            fileCheckUtil.validateFile(multipartFile);

            File file = new File();
            String extention = Objects.requireNonNull(multipartFile.getOriginalFilename())
                    .substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);

            String fileName = UUID.randomUUID() + "." + extention;

            file.setName(fileName);
            file.setContentType(multipartFile.getContentType());
            file.setSize(multipartFile.getSize());
            file.setExtension(extention);
            file.setPath(uploadDirectory + fileName);

            Path targetPath = Path.of(uploadDirectory, fileName);

            // Asinxron saqlash
            CompletableFuture.runAsync(() -> {
                try {
                    Files.copy(multipartFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
                }
            }).exceptionally(ex -> {
                throw new RuntimeException("Failed to save image: " + ex.getMessage(), ex);
            });

            return fileRepository.save(file);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File updateFile(MultipartFile multipartFile, File oldFile) {

        try {
            if (oldFile != null) {
                Files.deleteIfExists(Path.of(oldFile.getPath()));
            }
            return uploadFile(multipartFile);
        } catch (Exception e) {
            throw new RuntimeException("Update failed: " + e.getMessage(), e);
        }

    }

    public ResponseEntity<?> downloadFile(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found!"));

        Path path = Paths.get(file.getPath());
        if (!Files.exists(path)) {
            throw new RuntimeException("File not found on disk!");
        }

        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file from disk", e);
        }
    }

    @Override
    public void deleteFile(File file) {

        try {
            String path = file.getPath();
            Files.deleteIfExists(Path.of(path));
        }
        catch (Exception e) {
            throw new RuntimeException("Delete failed: " + e.getMessage(), e);
        }

    }

}
