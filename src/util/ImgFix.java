package util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImgFix {
    /** rotate image */
    public static BufferedImage getRotImg(BufferedImage img, int rotAngle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage newImg = new BufferedImage(w, h, img.getType());
        Graphics2D g2d = newImg.createGraphics();

        g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return newImg;
    }

    /** img layer builder */
    public static BufferedImage buildImg(BufferedImage[] imgs){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();
        BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
        Graphics2D g2d = newImg.createGraphics();

        for(BufferedImage img : imgs){
            g2d.drawImage(img, 0, 0, null);
        }
        g2d.dispose();

        return newImg;
    }

    /** rotate specific image at index only */
    public static BufferedImage getBuildRotImg(BufferedImage[] imgs, int rotAngle, int rotAtIndex){
        imgs[rotAtIndex] = getRotImg(imgs[rotAtIndex], rotAngle);
        return buildImg(imgs);
    }

    /** rotate specific image at index only + animation*/
    public static BufferedImage[] getBuildRotImg(BufferedImage[] imgs, BufferedImage secondImage, int rotAngle){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();

        // rotate second image
        secondImage = getRotImg(secondImage, rotAngle);
        BufferedImage[] arr = new BufferedImage[imgs.length];

        // merge all animated images with the rotated image
        for(int i = 0; i < imgs.length; i++) {
            BufferedImage newImg = new BufferedImage(w, h, imgs[i].getType());
            Graphics2D g2d = newImg.createGraphics();
            g2d.drawImage(imgs[i], 0, 0, null);
            g2d.drawImage(secondImage, 0, 0, null);
            g2d.dispose();

            arr[i] = newImg;
        }

        return arr;
    }
}
