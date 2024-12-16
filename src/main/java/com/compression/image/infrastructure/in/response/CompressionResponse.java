package com.compression.image.infrastructure.in.response;

import java.io.File;
import java.util.List;

public record CompressionResponse(
        List<File> files
) { }