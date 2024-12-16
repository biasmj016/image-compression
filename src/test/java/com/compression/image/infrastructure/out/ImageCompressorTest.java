package com.compression.image.infrastructure.out;


import com.compression.image.domain.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageCompressorTest {

    private ImageCompressor imageCompressor;

    @BeforeEach
    void setUp() {
        imageCompressor = new ImageCompressor();
    }

    @Test
    void testCompressImage() throws IOException {
        File inputFile = new File("src/test/resources/test_img.jpg");
        MultipartFile multipartFile = new MockMultipartFile("file", new FileInputStream(inputFile));

        Image image = new Image(new FileUploader.FileUploaderAdapter(multipartFile), "jpg", 50);

        File compressedFile = imageCompressor.compressImage(image);

        assertNotNull(compressedFile);
        assertTrue(compressedFile.exists());
        assertTrue(compressedFile.length() > 0);

        Files.deleteIfExists(compressedFile.toPath());
    }
}