package com.tchokoapps.springboot.ecommerce.common.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ThumbnailatorUtil {

    private ThumbnailatorUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void resizeImage(String inputFile, String outputFile, int targetWidth, int targetHeight) throws IOException {

        Objects.requireNonNull(inputFile, "Input file cannot be null");
        Objects.requireNonNull(outputFile, "Output file cannot be null");

        File inFile = new File(inputFile);
        File outFile = new File(outputFile);

        if (!inFile.exists()) {
            throw new IllegalArgumentException("Input file does not exist");
        }

        if (!outFile.createNewFile()) {
            throw new IllegalArgumentException("Output file could not be created");
        }

        Thumbnails.of(inFile).size(targetWidth, targetHeight).toFile(outFile);

        Thumbnails.of(inFile)
                .size(targetWidth, targetHeight)
                .toFile(outFile);
    }
}
