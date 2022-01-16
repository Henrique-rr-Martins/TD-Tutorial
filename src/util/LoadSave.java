package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class LoadSave {
    public static BufferedImage getSpriteAtlas(){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        try {
            img = ImageIO.read(is);
        }catch (Exception e){
            e.printStackTrace();
        }

        return img;
    }
}
