package com.fat.BUS.Services;

import com.fat.BUS.Abstractions.Services.IUploadImageService;
import com.fat.Contract.Exceptions.UploadImageException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class UploadImageService implements IUploadImageService {
    @Override
    public String uploadImage(String imageName, Path sourcePath) {
       try{
           // 1. Lấy đường dẫn gốc dự án
           String projectRoot = System.getProperty("user.dir"); // WaterManagementProject
           String destinationPath = projectRoot + File.separator + "product_images";
           File destDir = new File(destinationPath);

           // 3. Tạo thư mục nếu chưa có
           if (!destDir.exists()) {
               destDir.mkdir();
           }
           String fileName = System.currentTimeMillis() + "_" + imageName;

           File destFile = new File(destDir, fileName);
           // 5. Copy file
           Files.copy(sourcePath, destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
           return fileName;
       }
       catch (Exception ex){
           ex.printStackTrace();
           throw new UploadImageException("Lỗi khi tải ảnh lên: " + ex.getMessage());
       }
    }
}
