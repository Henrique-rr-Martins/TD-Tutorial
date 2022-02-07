package enemies;

import util.GlobalValuesUtil;

import java.awt.*;
import static util.ConstantsUtil.Direction.*;

public abstract class Enemy {
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
        this.bounds = new Rectangle((int)x, (int)y, GlobalValuesUtil.SPRITE_SIZE, GlobalValuesUtil.SPRITE_SIZE);
        this.lastDir = -1;
    }

    public void move(float speed, int dir){
        this.lastDir = dir;

        switch(dir){
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
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
