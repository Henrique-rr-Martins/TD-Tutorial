package managers;

import enemies.*;
import scenes.Playing;
import util.ConstantsUtil;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static util.ConstantsUtil.Direction.*;
import static util.ConstantsUtil.Tiles.ROAD_TILE;
import static util.ConstantsUtil.Enemies.*;

public class EnemyManager {
    private final Playing playing;
    private final BufferedImage[] enemyImgs;
    private final ArrayList<Enemy> enemies = new ArrayList<>();

    private final int tileSize = GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE;
    private final int spriteSize = GlobalValuesUtil.SPRITE_SIZE;

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
            enemyImgs[i] = atlas.getSubimage(i * spriteSize, spriteSize, spriteSize, spriteSize);
    }

    public void update(){

        for(Enemy e : enemies)
            this.updateEnemyMove(e, getSpeed(e.getEnemyType()));
    }

    private boolean isAtEnd(Enemy e) {
        return e.getX() == this.playing.getEndPathPoint().getXCord() * tileSize &&
                e.getY() == this.playing.getEndPathPoint().getYCord() * tileSize;
    }

    private void updateEnemyMove(Enemy e, float speed) {
        int lastDir = e.getLastDir();

        if(lastDir == -1)
            this.setNewDirectionAndMove(e, speed);

        int newX = (int) (e.getX() + this.getSpeedAndWidth(speed, lastDir));
        int newY = (int) (e.getY() + this.getSpeedAndHeight(speed, lastDir));

        if(this.getTileType(newX, newY) == ROAD_TILE){
            // keep moving in same direction
            e.move(speed, e.getLastDir());
        } else if(this.isAtEnd(e)){
            // reached the end
            System.out.println("Lives lost!");
        } else {
            // find new direction
            this.setNewDirectionAndMove(e, speed);
        }
    }

    private void setNewDirectionAndMove(Enemy e, float speed) {
        int dir = e.getLastDir();

        // move into the current until 100%;
        int xCord = (int) (e.getX() / spriteSize);
        int yCord = (int) (e.getY() / spriteSize);

        this.fixEnemyOffSetTile(e, dir, xCord, yCord);

        if(isAtEnd(e))
            return;

        if(dir == LEFT || dir == RIGHT){
            int newY = (int) (e.getY() + this.getSpeedAndHeight(speed, UP));
            if(this.getTileType((int) e.getX(), newY) == ROAD_TILE)
                e.move(speed,UP);
            else
                e.move(speed,DOWN);
        } else {
            int newX = (int) (e.getX() + this.getSpeedAndWidth(speed, RIGHT));
            if(this.getTileType(newX, (int) e.getY()) == ROAD_TILE)
                e.move(speed, RIGHT);
            else
                e.move(speed, LEFT);
        }
    }

    private void fixEnemyOffSetTile(Enemy e, int dir, int xCord, int yCord) {
        switch(dir){
            case RIGHT:
                // there are 20 tiles, but as array we need to subtract 1 to work with max_tile = 19
                if(xCord < GlobalValuesUtil.SCREEN_WIDTH / spriteSize - 1)
                    xCord++;
                break;
            case DOWN:
                // there are 20 tiles, but as array we need to subtract 1 to work with max_tile = 19
                if(yCord < GlobalValuesUtil.SCREEN_HEIGHT / spriteSize - 1)
                    yCord++;
                break;
        }

        e.setPos(xCord * spriteSize, yCord * spriteSize);
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x, y);
    }

    private float getSpeedAndHeight(float speed, int dir) {
        if(dir == UP)
            return -speed;
        else if(dir == DOWN)
            return speed + spriteSize;

        return 0;
    }

    private float getSpeedAndWidth(float speed, int dir) {
        if(dir == LEFT)
            return -speed;
        else if(dir == RIGHT)
            return speed + spriteSize;

        return 0;
    }

    public void addEnemy(int enemyType){
        int x = this.playing.getStartPathPoint().getXCord();
        int y = this.playing.getStartPathPoint().getYCord();
        switch (enemyType) {
            case ORC -> enemies.add(new Orc(x * tileSize, y * tileSize, ORC));
            case BAT -> enemies.add(new Bat(x * tileSize, y * tileSize, BAT));
            case KNIGHT -> enemies.add(new Knight(x * tileSize, y * tileSize, KNIGHT));
            case WOLF -> enemies.add(new Wolf(x * tileSize, y * tileSize, WOLF));
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
