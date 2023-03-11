package com.tchokoapps.springboot.ecommerce.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class FileUploadUtil {

    private static final String UPLOAD_DIR = "photos";

    private FileUploadUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String saveFile(MultipartFile multipartFile) throws IOException {

        Objects.requireNonNull(multipartFile, "Multipart file cannot be null");

        final String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        final String fileExtension = FilenameUtils.getExtension(originalFileName);
        Objects.requireNonNull(fileExtension, "File extension cannot be null");

        final String fileName = UUID.randomUUID().toString().replaceAll("-", "").concat(".").concat(fileExtension);
        File uploadDir = new File(UPLOAD_DIR);

        if (!uploadDir.exists()) {
            boolean mkdirResult = uploadDir.mkdir();
            if (!mkdirResult)
                throw new IOException("Could not create upload directory");
        }

        File destFile = new File(uploadDir.getAbsolutePath() + File.separator + fileName);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), destFile);
        return fileName;
    }
}
