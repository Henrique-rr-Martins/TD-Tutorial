package enemies;

import util.GlobalValuesUtil;

import java.awt.*;
import static util.ConstantsUtil.Direction.*;

public class Enemy {
    private float x, y;
    private Rectangle bounds;
    private int health;
    private int id;
    private int enemyType;
    private int lastDir;

    public Enemy(float x, float y, int id, int enemyType){
        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        this.bounds = new Rectangle((int)x, (int)y, 32, 32);
        this.lastDir = RIGHT;
    }

    public void move(int dir){
        this.lastDir = dir;

        switch(dir){
            case LEFT:
                this.x -= GlobalValuesUtil.ENEMY_SPEED;
                break;
            case UP:
                this.y -= GlobalValuesUtil.ENEMY_SPEED;
                break;
            case RIGHT:
                this.x += GlobalValuesUtil.ENEMY_SPEED;
                break;
            case DOWN:
                this.y += GlobalValuesUtil.ENEMY_SPEED;
                break;
        }
    }

    public void setPos(int x, int y){

        // dont use this one for move, this is for position fix
        this.x = x;
        this.y = y;
    }


    public float getX() { return x; }
    public float getY() { return y; }
    public Rectangle getBounds() { return bounds; }
    public int getHealth() { return health; }
    public int getId() { return id; }
    public int getEnemyType() { return enemyType; }
    public int getLastDir(){ return this.lastDir; }
}
