package com.compression.image.domain;

import com.compression.image.infrastructure.out.FileUploader;

import java.util.List;

public record Image (
        FileUploader files,
        String format,
        int quality
) {
    private static final List<String> VALID_FORMATS = List.of("jpg", "png");

    public Image {
        if (quality < 0 || quality > 100) {
            throw new IllegalArgumentException("Compression quality must be between 0 and 100.");
        }
        if (files == null) {
            throw new IllegalArgumentException("At least one file must be provided.");
        }
        if (format == null || format.isBlank()) {
            throw new IllegalArgumentException("Format must be specified.");
        }
        if (!VALID_FORMATS.contains(format.toLowerCase())) {
            throw new IllegalArgumentException("Format must be either 'jpg' or 'png'.");
        }
    }
}