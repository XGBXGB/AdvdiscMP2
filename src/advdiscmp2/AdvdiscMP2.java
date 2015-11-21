package advdiscmp2;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.Raster;
import java.awt.Color;
import static java.awt.Color.red;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static sun.swing.MenuItemLayoutHelper.max;

public class AdvdiscMP2 {

    public static void toGray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = (int) (c.getRed() * 0.21);
                int green = (int) (c.getGreen() * 0.72);
                int blue = (int) (c.getBlue() * 0.07);
                int sum = red + green + blue;
                Color newColor = new Color(sum, sum, sum);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
    }

    public static void toBrighten(BufferedImage image, int add) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = (int) (c.getRed() + add);
                int green = (int) (c.getGreen() + add);
                int blue = (int) (c.getBlue() + add);
                int sum = red + green + blue;
                Color newColor = new Color(red, green, blue);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
    }

    public static void toThreshold(BufferedImage image, int threshold) {
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = (int) (c.getRed() * 0.21);
                int green = (int) (c.getGreen() * 0.72);
                int blue = (int) (c.getBlue() * 0.07);
                int sum = red + green + blue;
                Color newColor = new Color(0, 0, 0);
                if (sum > threshold) {
                    newColor = new Color(255, 255, 255);
                } else {
                    newColor = new Color(0, 0, 0);
                }
                image.setRGB(j, i, newColor.getRGB());
            }
        }
    }

    public static BufferedImage toConvolute(BufferedImage image, double[][] kernel, int kernelWidth, int kernelHeight) {

        int widthHalfOffset = kernelWidth / 2;
        int heightHalfOffset = kernelHeight / 2;
        int width = image.getWidth();
        int height = image.getHeight();
        int w = width;
        int h = height;
        BufferedImage filteredImage = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int r = 0, g = 0, b = 0, a = 0;

                for (int kernelX = 0; kernelX < kernelWidth; kernelX++) {
                    for (int kernelY = 0; kernelY < kernelHeight; kernelY++) {
                        int pixelX = (j - widthHalfOffset + kernelX + w) % w;
                        int pixelY = (i - heightHalfOffset + kernelX + h) % h;
                        double weight = kernel[kernelX][kernelY];
                        Color currPixel = new Color(image.getRGB(pixelX, pixelY));
                        r += currPixel.getRed() * weight;
                        g += currPixel.getGreen() * weight;
                        b += currPixel.getBlue() * weight;
                        //a += c.getAlpha() * wt;
                        //System.out.println(" r " + r + " g " + g + " b " + b + " wt " + wt);\
                    }
                }
                if(r<0)r=0;
                if(r>255)r=255;
                if(g<0)g=0;
                if(g>255)g=255;
                if(b<0)b=0;
                if(b>255)b=255;
                
                Color newPixel = new Color(r, g, b);
                filteredImage.setRGB(j, i, newPixel.getRGB());
            }
        }
        return filteredImage;
    }

    public static void toSharpen(BufferedImage image) {
        Kernel kernel = new Kernel(3, 3, new float[]{-50, -50, -5, -5, 41, -5, -5,
            -5, -5});
        BufferedImageOp op = new ConvolveOp(kernel);
        image = op.filter(image, null);
    }

    static public void main(String args[]) throws IOException {
        File input = new File("..\\AdvdiscMP2\\testImage.jpg");
        BufferedImage image = ImageIO.read(input);
        //float[] f = new float[]{(float) 0.5,(float) 0.5,(float) 0.5,(float) 0.5};
        //toSharpen(image);

        double[][] kernel = {{0, -5, 0}, {-5, 21, -5}, {0, -5, 0}};
        
  
        
        BufferedImage newImage = toConvolute(image,kernel,3,3);
        File output = new File("..\\AdvdiscMP2\\processed-" + input.getName());
        ImageIO.write(newImage, "jpg", output);
    }

    
}
