package managers;

import enemies.*;
import scenes.Playing;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static util.ConstantsUtil.Direction.*;
import static util.ConstantsUtil.Tiles.ROAD_TILE;
import static util.ConstantsUtil.Enemies.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] enemyImgs;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        this.enemyImgs = new BufferedImage[GlobalValuesUtil.ENEMY_IMAGES_AMOUNT];
        this.loadEnemyImgs();

        this.addEnemy(ORC);
        this.addEnemy(BAT);
        this.addEnemy(KNIGHT);
        this.addEnemy(WOLF);
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for(int i = 0; i < GlobalValuesUtil.ENEMY_IMAGES_AMOUNT; i++)
            enemyImgs[i] = atlas.getSubimage(i * GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE);
    }

    public void update(){

        for(Enemy e : enemies)
            this.updateEnemyMove(e);
    }

    private boolean isAtEnd(Enemy e) {
        if(e.getX() == this.playing.getEndPathPoint().getXCord() * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE &&
        e.getY() == this.playing.getEndPathPoint().getYCord() * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE)
            return true;

        return false;
    }

    private void updateEnemyMove(Enemy e) {
        if(e.getLastDir() == -1)
            this.setNewDirectionAndMove(e);

        int newX = (int) (e.getX() + this.getSpeedAndWidth(e.getLastDir()));
        int newY = (int) (e.getY() + this.getSpeedAndHeight(e.getLastDir()));

        if(this.getTileType(newX, newY) == ROAD_TILE){
            // keep moving in same direction
            e.move(e.getLastDir());
        } else if(this.isAtEnd(e)){
            // reached the end
            System.out.println("Lives lost!");
        } else {
            // find new direction
            this.setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();

        // move into the current until 100%;
        int xCord = (int) (e.getX() / GlobalValuesUtil.SPRITE_SIZE);
        int yCord = (int) (e.getY() / GlobalValuesUtil.SPRITE_SIZE);

        this.fixEnemyOffSetTile(e, dir, xCord, yCord);

        if(isAtEnd(e))
            return;

        if(dir == LEFT || dir == RIGHT){
            int newY = (int) (e.getY() + this.getSpeedAndHeight(UP));
            if(this.getTileType((int) e.getX(), newY) == ROAD_TILE)
                e.move(UP);
            else
                e.move(DOWN);
        } else {
            int newX = (int) (e.getX() + this.getSpeedAndWidth(RIGHT));
            if(this.getTileType(newX, (int) e.getY()) == ROAD_TILE)
                e.move(RIGHT);
            else
                e.move(LEFT);
        }
    }

    private void fixEnemyOffSetTile(Enemy e, int dir, int xCord, int yCord) {
        switch(dir){
            case RIGHT:
                // there are 20 tiles, but as array we need to subtract 1 to work with max_tile = 19
                if(xCord < GlobalValuesUtil.SCREEN_WIDTH / GlobalValuesUtil.SPRITE_SIZE - 1)
                    xCord++;
                break;
            case DOWN:
                // there are 20 tiles, but as array we need to subtract 1 to work with max_tile = 19
                if(yCord < GlobalValuesUtil.SCREEN_HEIGHT / GlobalValuesUtil.SPRITE_SIZE - 1)
                    yCord++;
                break;
        }

        e.setPos(xCord * GlobalValuesUtil.SPRITE_SIZE, yCord * GlobalValuesUtil.SPRITE_SIZE);
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x, y);
    }

    private float getSpeedAndHeight(int dir) {
        if(dir == UP)
            return -GlobalValuesUtil.ENEMY_SPEED;
        else if(dir == DOWN)
            return GlobalValuesUtil.ENEMY_SPEED + GlobalValuesUtil.SPRITE_SIZE;

        return 0;
    }

    private float getSpeedAndWidth(int dir) {
        if(dir == LEFT)
            return -GlobalValuesUtil.ENEMY_SPEED;
        else if(dir == RIGHT)
            return GlobalValuesUtil.ENEMY_SPEED + GlobalValuesUtil.SPRITE_SIZE;

        return 0;
    }

    public void addEnemy(int enemyType){
        int x = this.playing.getStartPathPoint().getXCord();
        int y = this.playing.getStartPathPoint().getYCord();
        switch (enemyType) {
            case ORC -> enemies.add(new Orc(x * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE, y * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE, ORC));
            case BAT -> enemies.add(new Bat(x * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE, y * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE, BAT));
            case KNIGHT -> enemies.add(new Knight(x * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE, y * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE, KNIGHT));
            case WOLF -> enemies.add(new Wolf(x * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE, y * GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE, WOLF));
        }
    }

    public void draw(Graphics g){
        this.drawEnemy(enemies, g);
    }

    private void drawEnemy(ArrayList<Enemy> enemies, Graphics g) {

        for (Enemy e : enemies)
            g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
    }
}
