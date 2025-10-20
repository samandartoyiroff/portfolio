package uz.tuit.portfolio.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.coyote.BadRequestException;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScriptImageCheckUtil {

    @Value("${file.upload.directory.file}")
    private String uploadDirectory;

    @Value("${file.upload.size}")
    private long maxFileSize;

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif");
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/gif"
    );

    /**
     * Yagona tekshiruvchi funksiya:
     * 1) Fayl bo‘sh emasligini tekshiradi
     * 2) Fayl hajmini tekshiradi
     * 3) Fayl kengaytmasini tekshiradi
     * 4) MIME turini va fayl signaturasini tekshiradi
     * 5) Upload pathni xavfsizligini tekshiradi
     */
    public void validateFile(MultipartFile file) throws IOException, BadRequestException {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is empty or missing");
        }

        // 1️⃣ Fayl hajmi
        if (file.getSize() > maxFileSize) {
            throw new BadRequestException("Max file size: " + (maxFileSize / 1024 / 1024) + "MB");
        }

        // 2️⃣ Fayl kengaytmasi
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new BadRequestException("File name is missing");
        }
        String extension = FilenameUtils.getExtension(originalFileName).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new BadRequestException("Only jpg, jpeg, png or gif files allowed");
        }

        // 3️⃣ Fayl MIME turi va signaturasi
        Tika tika = new Tika();
        String detectedMimeType;
        try (InputStream inputStream = file.getInputStream()) {
            detectedMimeType = tika.detect(inputStream);
        }
        if (!ALLOWED_CONTENT_TYPES.contains(detectedMimeType)) {
            throw new BadRequestException("Invalid file signature: " + detectedMimeType + ", only JPEG, PNG, or GIF allowed");
        }

        boolean isValid = switch (detectedMimeType) {
            case "image/jpeg" -> extension.equals("jpg") || extension.equals("jpeg");
            case "image/png" -> extension.equals("png");
            case "image/gif" -> extension.equals("gif");
            default -> false;
        };
        if (!isValid) {
            throw new BadRequestException("MIME type (" + detectedMimeType + ") does not match file extension: " + extension);
        }

        // 4️⃣ Fayl yo‘lini tekshirish (Path Traversal oldini olish)
        Path resolvedPath = Paths.get(uploadDirectory, "images", originalFileName).normalize();
        if (!resolvedPath.startsWith(Paths.get(uploadDirectory).normalize())) {
            throw new IOException("Incorrect file path: " + originalFileName);
        }

        log.info("File '{}' successfully validated: type={}, size={} bytes",
                originalFileName, detectedMimeType, file.getSize());
    }

}
