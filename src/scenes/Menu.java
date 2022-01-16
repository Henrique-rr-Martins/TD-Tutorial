package scenes;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends GameScene implements SceneMethods{
    private final Random random;

    // Image comes from the Game instance
    private BufferedImage img;
    // List of sprites
    public ArrayList<BufferedImage> sprites;
    private final int WIDTH;
    private final int HEIGHT;

    public Menu(Game game) {
        super(game);

        this.random = new Random();

        // Sprite dimensions
        this.WIDTH = 32;
        this.HEIGHT = 32;

        // Import default sprites
        this.importImg();
        // Slice the sprite sheet
        this.loadSprites(img, 10, 10);
    }

    @Override
    public void render(Graphics g) {
        for(int y = 0; y < 20; y++) {
            for(int x = 0; x < 20; x++){
                g.drawImage(sprites.get(getRndInt()), x * 32, y * 32, null);
            }
        }
    }

    private void importImg(){
        try(InputStream is = getClass().getResourceAsStream("/spriteatlas.png")){
            this.img = ImageIO.read(is);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Cuts the image in many sprites and stores in the sprites array.
     * Prepares the sprite array to gives the sprites we need based on the main class image
     *
     * @param img get a BufferedImage which is the sprite
     * @param amountY the amount of positions in the HEIGHT
     * @param amountX the amount of positions in the WIDTH
     */
    private void loadSprites(BufferedImage img, int amountY, int amountX) {
        this.sprites = new ArrayList<>();
        for (int posY = 0; posY < amountY; posY++) {
            for (int posX = 0; posX < amountX; posX++) {
                this.sprites.add(this.getSprite(posX, posY));
            }
        }
    }

    /**
     * @param posX between 0 to 9. Needs to be a position which is equals to amonut of squares -1;
     * @param posY between 0 to 9 Needs to be a position which is equals to amonut of squares -1;
     *
     * @return a subImage from the object BufferedImage using the default WIDTH and HEIGHT
     */
    private BufferedImage getSprite(int posX, int posY){
        return this.img.getSubimage(posX * WIDTH, posY * HEIGHT, WIDTH, HEIGHT);
    }

    private int getRndInt(){
        return random.nextInt(100);
    }
}
