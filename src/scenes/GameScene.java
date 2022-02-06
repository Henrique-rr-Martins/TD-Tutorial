package scenes;

import main.Game;
import objects.PathPoint;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameScene {

    protected int[][] lvl;
    private Game game;
    protected PathPoint start, end;

    // index of sprite in animation
    protected int animationIndex;
    // animation speed
    protected int tick;

    public GameScene(Game game){
        this.game = game;

        this.loadDefaultLevel();
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
        ArrayList<PathPoint> points = LoadSave.getLevelPathPoints("new_level");
        start = points.get(0);
        end = points.get(1);
    }

    public Game getGame(){ return this.game; }

    protected boolean isAnimation(int spriteId) {
        return getGame().getTileManager().isSpriteAnimation(spriteId);
    }

    protected void updateTick() {
        this.tick++;
        if(this.tick >= GlobalValuesUtil.TILE_ANIMATION_SPEED){
            this.tick = 0;
            this.animationIndex++;
        }
        if(this.animationIndex >= 4)
            this.animationIndex = 0;
    }

    protected void drawLevel(Graphics g){
        for(int y = 0; y < lvl.length; y++){
            for(int x = 0; x < lvl[y].length; x++){
                int id = lvl[y][x];
                if(this.isAnimation(id))
                    g.drawImage(this.getSprite(id, animationIndex), x * 32, y * 32, null);
                else
                    g.drawImage(this.getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    protected BufferedImage getSprite(int spriteId){ return this.getGame().getTileManager().getSprite(spriteId); }
    protected BufferedImage getSprite(int spriteId, int animationIndex){
        return this.getGame().getTileManager().getAnimatedSprite(spriteId, animationIndex);
    }

    public PathPoint getStartPathPoint(){ return this.start; }
    public PathPoint getEndPathPoint(){ return this.end; }
}
