package managers;

import objects.Tile;
import util.GlobalValuesUtil;
import util.ImgFix;
import util.LoadSave;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static util.ConstantsUtil.Tiles.*;
import static util.ConstantsUtil.Tiles.WATER_TILE;
import static util.StringUtil.*;

public class TileManager {
    public Tile GRASS, WATER, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R, BL_WATER_CORNER,
            TL_WATER_CORNER, TR_WATER_CORNER, BR_WATER_CORNER, T_WATER, R_WATER, B_WATER, L_WATER, TL_ISLE, TR_ISLE,
            BR_ISLE, BL_ISLE, PATH_START, PATH_END;
    public BufferedImage atlas;
    public ArrayList<Tile> tiles = new ArrayList<>();

    public ArrayList<Tile> roadsStraight = new ArrayList<>();
    public ArrayList<Tile> roadsCorner = new ArrayList<>();
    public ArrayList<Tile> waterCorner = new ArrayList<>();
    public ArrayList<Tile> beaches = new ArrayList<>();
    public ArrayList<Tile> islands = new ArrayList<>();

    public TileManager(){
        this.loadAtlas();
        this.createTiles();
    }

    private void createTiles() {
        int id = 0;
        tiles.add(GRASS = new Tile(getSprite(9, 0), id++, GRASS_TILE));
        tiles.add(WATER = new Tile(getAnimatedSprites(0, 0), id++, WATER_TILE));

        roadsStraight.add(ROAD_LR = new Tile(getSprite(8, 0), id++, ROAD_TILE));
        roadsStraight.add(ROAD_TB = new Tile(ImgFix.getRotImg(getSprite(8, 0), 90), id++, ROAD_TILE));

        roadsCorner.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, ROAD_TILE));
        roadsCorner.add(ROAD_L_TO_B = new Tile(ImgFix.getRotImg(getSprite(7, 0), 90), id++, ROAD_TILE));
        roadsCorner.add(ROAD_L_TO_T = new Tile(ImgFix.getRotImg(getSprite(7, 0), 180), id++, ROAD_TILE));
        roadsCorner.add(ROAD_T_TO_R = new Tile(ImgFix.getRotImg(getSprite(7, 0), 270), id++, ROAD_TILE));

        waterCorner.add(BL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(5, 0), 0), id++, WATER_TILE));
        waterCorner.add(TL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(5, 0), 90), id++, WATER_TILE));
        waterCorner.add(TR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(5, 0), 180), id++, WATER_TILE));
        waterCorner.add(BR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(5, 0), 270), id++, WATER_TILE));

        beaches.add(T_WATER = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(6, 0), 0), id++, WATER_TILE));
        beaches.add(R_WATER = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(6, 0), 90), id++, WATER_TILE));
        beaches.add(B_WATER = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(6, 0), 180), id++, WATER_TILE));
        beaches.add(L_WATER = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(6, 0), 270), id++, WATER_TILE));

        islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(4, 0), 0), id++, WATER_TILE));
        islands.add(TR_ISLE = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(4, 0), 90), id++, WATER_TILE));
        islands.add(BR_ISLE = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(4, 0), 180), id++, WATER_TILE));
        islands.add(BL_ISLE = new Tile(ImgFix.getBuildRotImg(getAnimatedSprites(0,0), getSprite(4, 0), 270), id++, WATER_TILE));

        tiles.addAll(roadsStraight);
        tiles.addAll(roadsCorner);
        tiles.addAll(waterCorner);
        tiles.addAll(beaches);
        tiles.addAll(islands);
    }

    private BufferedImage[] getImgs(int firstX, int firstY, int secondX, int secondY){
        return new BufferedImage[]{this.getSprite(firstX, firstY), this.getSprite(secondX, secondY)};
    }

    private void loadAtlas() {
        this.atlas = LoadSave.getSpriteAtlas();
    }

    public Tile getTile(int id){ return tiles.get(id); }

    public BufferedImage getSprite(int id){ return this.tiles.get(id).getSprite(); }
    public BufferedImage getAnimatedSprite(int id, int animationIndex){
        return this.tiles.get(id).getSprite(animationIndex);
    }

    private BufferedImage[] getAnimatedSprites(int xCord, int yCord){
        BufferedImage[] animatedSprites = new BufferedImage[4];

        for(int i = 0; i < 4; i++)
            animatedSprites[i] = this.getSprite(xCord + i, yCord);

        return animatedSprites;
    }

    private BufferedImage getSprite(int xCord, int yCord){
        int size = GlobalValuesUtil.SPRITE_SIZE;
        return atlas.getSubimage(xCord * size, yCord * size, size, size);
    }

    /**
     *Uses the map array to figure out each tile type, so we can work on ROAD_TILE type
     *
     * @return typeArray with any tile type
     */
    public int[][] getTypeArray(){
        int[][] idArray = LoadSave.getLevelData();
        int[][] typeArr = new int[idArray.length][idArray[0].length];

        for(int y = 0; y < idArray.length; y++)
            for(int x = 0; x < idArray[0].length; x++){
                int id = idArray[y][x];
                typeArr[y][x] = tiles.get(id).getTileType();
            }

        return typeArr;
    }
    public boolean isSpriteAnimation(int spriteId){ return tiles.get(spriteId).isSpriteAnimation(); }
    public ArrayList<Tile> getRoadsStraight() { return roadsStraight; }
    public ArrayList<Tile> getRoadsCorner() { return roadsCorner; }
    public ArrayList<Tile> getWaterCorner() { return waterCorner; }
    public ArrayList<Tile> getBeaches() { return beaches; }
    public ArrayList<Tile> getIslands() { return islands; }
}
