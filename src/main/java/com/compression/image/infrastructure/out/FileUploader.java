package com.compression.image.infrastructure.out;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public interface FileUploader {
    File toFile();

    class FileUploaderAdapter implements FileUploader {

        private final MultipartFile multipartFile;

        public FileUploaderAdapter(MultipartFile multipartFile) {
            this.multipartFile = multipartFile;
        }

        @Override
        public File toFile() {
            try {
                File tempFile = File.createTempFile("uploaded_", multipartFile.getOriginalFilename());
                try (var outputStream = new FileOutputStream(tempFile)) {
                    multipartFile.getInputStream().transferTo(outputStream);
                }
                return tempFile;
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert MultipartFile to File: " + multipartFile.getOriginalFilename(), e);
            }
        }
    }
}