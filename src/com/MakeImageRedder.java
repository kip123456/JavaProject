package com;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class MakeImageRedder {
    public static void main(String[] args) {
        Random a = new Random(System.currentTimeMillis());
        for(int i=1;i<=100;++i)
        {
            System.out.println(a.nextGaussian(2,2));
        }
    }
}
