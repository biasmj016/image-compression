package com.compression.image.application.in;

import com.compression.image.domain.Image;
import com.compression.image.infrastructure.out.ImageCompressor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompressImageUseCase {

    private final ImageCompressor imageCompressor;

    public CompressImageUseCase(ImageCompressor imageCompressor) {
        this.imageCompressor = imageCompressor;
    }

    public List<File> compressImages(List<Image> images) throws IOException {
        return images.stream()
                .map(image -> {
                    try {
                        return imageCompressor.compressImage(image);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to compress image", e);
                    }
                })
                .collect(Collectors.toList());
    }
}