package com.tchokoapps.springboot.ecommerce.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class FileUploadUtil {

    private static final String UPLOAD_DIR = "upload";

    public static String saveFile(@NotNull MultipartFile multipartFile) throws IOException {

        final String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        final String fileExtension = FilenameUtils.getExtension(originalFileName);
        final String fileName = UUID.randomUUID().toString().replaceAll("-", "").concat(".").concat(fileExtension);
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        File destFile = new File(uploadDir.getAbsolutePath() + File.separator + fileName);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), destFile);
        return fileName;
    }
}
