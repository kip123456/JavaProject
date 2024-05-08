package com;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MakeImageRedder {
    public static void main(String[] args) {
        try {
            File input = new File("./data/img/Player2.png");
            BufferedImage image = ImageIO.read(input);

            int width = image.getWidth();
            int height = image.getHeight();

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Color c = new Color(image.getRGB(j, i), true);
                    int red = (int)(c.getRed() * 2);
                    int green = c.getGreen();
                    int blue = c.getBlue();
                    int alpha = c.getAlpha();  // 获取透明度
                    red = Math.min(255, red);  // 确保红色值不超过255

                    Color newColor = new Color(red, green, blue, alpha);  // 保留原有透明度
                    image.setRGB(j, i, newColor.getRGB());
                }
            }

            File output = new File("redder.png");
            ImageIO.write(image, "png", output);  // 输出PNG格式的图片

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
