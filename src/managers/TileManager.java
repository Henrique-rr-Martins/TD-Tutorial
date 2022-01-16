package managers;

import objects.Tile;
import util.LoadSave;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileManager {
    public Tile GRASS, WATER, ROAD;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public TileManager(){
        this.loadAtlas();
        this.createTiles();
    }

    private void createTiles() {
        // add grass tile
        this.GRASS = new Tile(this.getSprite(8, 1));
        this.tiles.add(GRASS);

        // add water tile
        this.WATER = new Tile(this.getSprite(0, 6));
        this.tiles.add(WATER);

        // add road tile
        this.ROAD = new Tile(this.getSprite(9, 0));
        this.tiles.add(ROAD);
    }

    private void loadAtlas() {
        this.atlas = LoadSave.getSpriteAtlas();
    }

    public BufferedImage getSprite(int id){
        return this.tiles.get(id).getSprite();
    }

    private BufferedImage getSprite(int xCord, int yCord){
        return atlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
    }
}
