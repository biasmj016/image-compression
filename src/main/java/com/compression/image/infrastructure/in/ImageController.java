package com.compression.image.infrastructure.in;

import com.compression.image.application.in.CompressImageUseCase;
import com.compression.image.domain.Image;
import com.compression.image.global.response.ApiResponse;
import com.compression.image.infrastructure.in.response.CompressionResponse;
import com.compression.image.infrastructure.out.FileUploader.FileUploaderAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.compression.image.global.response.ApiResponse.success;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final CompressImageUseCase compressImageUseCase;

    public ImageController(CompressImageUseCase compressImageUseCase) {
        this.compressImageUseCase = compressImageUseCase;
    }

    @PostMapping("/compress")
    public ApiResponse<CompressionResponse> compressImages(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("format") String format,
            @RequestParam("quality") int quality) throws IOException {

        List<Image> images = files.stream()
                .map(file -> new Image(new FileUploaderAdapter(file), format, quality))
                .toList();

        CompressionResponse response = new CompressionResponse(compressImageUseCase.compressImages(images));
        return success(response);
    }
}