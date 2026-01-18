package com.fat.GUI.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImageHelper{
    public static ImageIcon resizeImage(ImageIcon icon, int width, int height){
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static String getImagePath(String imageName) {
        String projectDir = System.getProperty("user.dir");
        String imagePath = projectDir + File.separator + "product_images" + File.separator + imageName;
        System.out.println(imagePath);
       return imagePath;
    }
}
