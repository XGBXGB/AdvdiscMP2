/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Christian Cote
 */
public class ImageModel {

    private BufferedImage image;
    private BufferedImage filteredImage;
    
    public ImageModel() {
        image = null;
        filteredImage = null;
    }
    
    public BufferedImage getOriginal(){
        return image;
    }
    
    public BufferedImage getFiltered(){
        return filteredImage;
    }
    
    public void setOriginal(BufferedImage image){
        this.image = image;
    }
    
    public void setFiltered(BufferedImage image){
        filteredImage = image;
    }
    
    public BufferedImage filterImage(double[][] kernel, int kernelWidth, int kernelHeight, double divisor) {

        int widthHalfOffset = kernelWidth / 2;
        int heightHalfOffset = kernelHeight / 2;
        int width = image.getWidth();
        int height = image.getHeight();
        int w = width;
        int h = height;
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int r = 0, g = 0, b = 0, a = 0;

                for (int kernelX = 0; kernelX < kernelWidth; kernelX++) {
                    for (int kernelY = 0; kernelY < kernelHeight; kernelY++) {
                        int pixelX = (j - widthHalfOffset + kernelX + w) % w;
                        int pixelY = (i - heightHalfOffset + kernelX + h) % h;
                        double weight = kernel[kernelX][kernelY];
                        Color currPixel = new Color(image.getRGB(pixelX, pixelY));
                        r += currPixel.getRed() * (weight/divisor);
                        g += currPixel.getGreen() * (weight/divisor);
                        b += currPixel.getBlue() * (weight/divisor);
                    }
                }
                if(r<0)r=0;
                if(r>255)r=255;
                if(g<0)g=0;
                if(g>255)g=255;
                if(b<0)b=0;
                if(b>255)b=255;
                
                Color newPixel = new Color(r, g, b);
                newImage.setRGB(j, i, newPixel.getRGB());
            }
        }
        return newImage;
    }
}
