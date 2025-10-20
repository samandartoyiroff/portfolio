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
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScriptFileCheckUtil {

    @Value("${file.upload.directory.file}")
    private String uploadDirectory;

    @Value("${file.upload.size}")
    private long maxFileSize;

    // ✅ MIME turi ↔ extension mosligi
    private static final Map<String, Set<String>> ALLOWED_TYPES = Map.ofEntries(
            Map.entry("application/pdf", Set.of("pdf")),
            Map.entry("application/msword", Set.of("doc")),
            Map.entry("application/vnd.openxmlformats-officedocument.wordprocessingml.document", Set.of("docx")),
            Map.entry("application/vnd.ms-excel", Set.of("xls")),
            Map.entry("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", Set.of("xlsx")),
            Map.entry("application/vnd.ms-powerpoint", Set.of("ppt")),
            Map.entry("application/vnd.openxmlformats-officedocument.presentationml.presentation", Set.of("pptx")),
            Map.entry("text/plain", Set.of("txt")),
            Map.entry("text/csv", Set.of("csv")),
            Map.entry("application/rtf", Set.of("rtf")),
            Map.entry("application/vnd.oasis.opendocument.text", Set.of("odt")),
            Map.entry("application/vnd.oasis.opendocument.spreadsheet", Set.of("ods")),
            Map.entry("application/vnd.oasis.opendocument.presentation", Set.of("odp")),
            Map.entry("image/jpeg", Set.of("jpg", "jpeg")),
            Map.entry("image/png", Set.of("png")),
            Map.entry("image/gif", Set.of("gif")),
            Map.entry("application/x-tika-ooxml", Set.of("docx", "xlsx", "pptx"))
    );

    /**
     * Faylni to‘liq validatsiya qiladi:
     * 1️⃣ Bo‘sh emasligini
     * 2️⃣ Hajmini
     * 3️⃣ Kengaytmasini
     * 4️⃣ MIME turini va signaturasini
     * 5️⃣ Path traversal xavfsizligini
     */
    public void validateFile(MultipartFile file) throws IOException, BadRequestException {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("File is empty or missing");
        }

        // 1️⃣ Fayl hajmi
        if (file.getSize() > maxFileSize) {
            throw new BadRequestException("File too large. Max size: " + (maxFileSize / 1024 / 1024) + "MB");
        }

        // 2️⃣ Fayl nomi va kengaytmasi
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new BadRequestException("File name is missing");
        }

        String extension = FilenameUtils.getExtension(originalFileName).toLowerCase();
        if (extension.isBlank()) {
            throw new BadRequestException("File extension missing");
        }

        // 3️⃣ MIME turi aniqlash
        Tika tika = new Tika();
        String detectedMimeType;
        try (InputStream inputStream = file.getInputStream()) {
            detectedMimeType = tika.detect(inputStream);
        }

        if (!ALLOWED_TYPES.containsKey(detectedMimeType)) {
            throw new BadRequestException("Unsupported file type: " + detectedMimeType);
        }

        String contentType = file.getContentType();
        if (!ALLOWED_TYPES.containsKey(contentType)) {
            throw new BadRequestException("Unsupported file type: " + detectedMimeType);
        }

        // 4️⃣ MIME va extension mosligini tekshirish
        Set<String> allowedExtensions = ALLOWED_TYPES.get(detectedMimeType);
        if (!allowedExtensions.contains(extension)) {
            throw new BadRequestException("MIME type (" + detectedMimeType + ") does not match extension: " + extension);
        }

        // 5️⃣ Fayl yo‘lini tekshirish (Path Traversal oldini olish)
        Path resolvedPath = Paths.get(uploadDirectory, originalFileName).normalize();
        if (!resolvedPath.startsWith(Paths.get(uploadDirectory).normalize())) {
            throw new IOException("Invalid file path: " + originalFileName);
        }

        log.info("✅ File '{}' validated successfully. type={}, ext={}, size={} bytes",
                originalFileName, detectedMimeType, extension, file.getSize());
    }
}
