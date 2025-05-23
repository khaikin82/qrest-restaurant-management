package com.khaikin.qrest.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final String rootDir = "uploads";

    public String storeFile(MultipartFile file, String uploadDir, HttpServletRequest request) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String absoluteUploadPath = Paths.get(System.getProperty("user.dir"), rootDir, uploadDir).toString();
        Path uploadPath = Paths.get(absoluteUploadPath);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());

        String baseUrl = request.getScheme() + "://" +
                request.getServerName() + ":" +
                request.getServerPort();

        return baseUrl + "/" + uploadDir + "/" + fileName;
    }
}
