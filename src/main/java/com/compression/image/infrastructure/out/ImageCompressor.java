package com.compression.image.infrastructure.out;

import com.compression.image.domain.Image;
import org.springframework.stereotype.Component;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
public class ImageCompressor {

    public File compressImage(Image image) throws IOException {
        File inputFile = image.files().toFile();
        File outputFile = new File("compressed_" + inputFile.getName());

        BufferedImage inputImage = readImage(inputFile);
        ImageWriter writer = getImageWriter(image.format());

        try (var ios = ImageIO.createImageOutputStream(outputFile)) {
            writer.setOutput(ios);
            ImageWriteParam param = getImageWriteParam(writer, image.quality());
            writer.write(null, new IIOImage(inputImage, null, null), param);
        } finally {
            writer.dispose();
        }

        return outputFile;
    }

    private BufferedImage readImage(File inputFile) throws IOException {
        return Optional.ofNullable(ImageIO.read(inputFile))
                .orElseThrow(() -> new IOException("Failed to read image: " + inputFile.getAbsolutePath()));
    }

    private ImageWriter getImageWriter(String format) {
        return Optional.ofNullable(ImageIO.getImageWritersByFormatName(format).next())
                .orElseThrow(() -> new IllegalArgumentException("No ImageWriter found for format: " + format));
    }

    private ImageWriteParam getImageWriteParam(ImageWriter writer, int quality) {
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality / 100.0f);
        }
        return param;
    }
}