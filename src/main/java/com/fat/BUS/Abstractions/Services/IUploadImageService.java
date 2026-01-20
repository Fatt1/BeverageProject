package com.fat.BUS.Abstractions.Services;

import java.nio.file.Path;

public interface IUploadImageService {
    String uploadImage(String imageName, Path sourcePath);
}
