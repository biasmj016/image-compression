package com.compression.image.application.in;

import com.compression.image.domain.Image;
import com.compression.image.infrastructure.out.FileUploader;
import com.compression.image.infrastructure.out.ImageCompressor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompressImageUseCaseTest {

    @Mock
    private ImageCompressor imageCompressor;

    @InjectMocks
    private CompressImageUseCase compressImageUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCompressImages() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        FileUploader fileUploader = new FileUploader.FileUploaderAdapter(multipartFile);
        Image image = new Image(fileUploader, "jpg", 80);

        File compressedFile = new File("compressed_image.jpg");
        when(imageCompressor.compressImage(image)).thenReturn(compressedFile);

        List<File> result = compressImageUseCase.compressImages(List.of(image));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(compressedFile, result.get(0));

        verify(imageCompressor, times(1)).compressImage(image);
    }
}