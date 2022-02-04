package objects;

import java.awt.image.BufferedImage;

public class Tile {
    private final BufferedImage[] sprites;
    private final int id;
    private final int tileType;

    public Tile(BufferedImage sprite, int id, int tileType){
        this.sprites = new BufferedImage[]{sprite};
        this.id = id;
        this.tileType = tileType;
    }

    public Tile(BufferedImage[] sprites, int id, int tileType){
        this.sprites = sprites;
        this.id = id;
        this.tileType = tileType;
    }

    public BufferedImage getSprite(){ return this.sprites[0]; }
    public BufferedImage getSprite(int animationIndex){
        return this.sprites[animationIndex];
    }
    public boolean isSpriteAnimation(){ return this.sprites.length > 1; }
    public int getId() { return this.id; }
    public int getTileType() { return this.tileType; }
}
