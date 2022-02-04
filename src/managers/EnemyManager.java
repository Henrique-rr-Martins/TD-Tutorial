package managers;

import enemies.Enemy;
import scenes.Playing;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static util.ConstantsUtil.Direction.*;
import static util.ConstantsUtil.Tiles.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] enemyImgs;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        this.enemyImgs = new BufferedImage[GlobalValuesUtil.ENEMY_IMAGES_AMOUNT];
        this.loadEnemyImgs();

        this.addEnemy(96, 288);
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImgs[0] = atlas.getSubimage(0, 32, 32, 32);
        enemyImgs[1] = atlas.getSubimage(32, 32, 32, 32);
        enemyImgs[2] = atlas.getSubimage(32 * 2, 32, 32, 32);
        enemyImgs[3] = atlas.getSubimage(32 * 3, 32, 32, 32);
    }

    public void update(){

        for(Enemy e : enemies){
            // is next tile road(pos, dir)
            if(this.isNextTileRoad(e)){
//               e.move(e.getLastDir());
            }else {

            }
        }
    }

    private boolean isAtEnd(Enemy e) {

        return false;
    }

    private boolean isNextTileRoad(Enemy e) {
        // e pos
        // e dir
        // tile at new possible pos
        int newX = (int) (e.getX() + this.getSpeedAndWidth(e.getLastDir()));
        int newY = (int) (e.getY() + this.getSpeedAndHight(e.getLastDir()));

        if(this.getTileType(newX, newY) == ROAD_TILE){
            // keep moving in same direction
            e.move(e.getLastDir());
        } else if(this.isAtEnd(e)){
            // reached the end
        } else {
            // find new direction
            this.setNewDirectionAndMove(e);
        }

        return false;
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();

        // move into the current until 100%;
        int xCord = (int) (e.getX() / 32);
        int yCord = (int) (e.getY() / 32);

        this.fixEnemyOffSetTile(e, dir, xCord, yCord);

        if(dir == LEFT || dir == RIGHT){
            int newY = (int) (e.getY() + this.getSpeedAndHight(UP));
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
//            case LEFT:
//                if(xCord > 0)
//                    xCord--;
//                break;
//            case UP:
//                if(yCord > 0)
//                    yCord--;
//                break;
            case RIGHT:
                // there are 20 tiles, but as array we need to subtract 1 to work with max_tile = 19
                if(xCord < 19)
                    xCord++;
                break;
            case DOWN:
                // there are 20 tiles, but as array we need to subtract 1 to work with max_tile = 19
                if(yCord < 19)
                    yCord++;
                break;
        }

        e.setPos(xCord * 32, yCord * 32);
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x, y);
    }

    private float getSpeedAndHight(int dir) {
        if(dir == UP)
            return -GlobalValuesUtil.ENEMY_SPEED;
        else if(dir == DOWN)
            return GlobalValuesUtil.ENEMY_SPEED + 32;

        return 0;
    }

    private float getSpeedAndWidth(int dir) {
        if(dir == LEFT)
            return -GlobalValuesUtil.ENEMY_SPEED;
        else if(dir == RIGHT)
            return GlobalValuesUtil.ENEMY_SPEED + 32;

        return 0;
    }

    public void addEnemy(int x, int y){
        enemies.add(new Enemy(x, y, 0, 0));
    }

    public void draw(Graphics g){
        this.drawEnemy(enemies, g);
    }

    private void drawEnemy(ArrayList<Enemy> enemies, Graphics g) {

        for (Enemy e : enemies)
            g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
    }
}
