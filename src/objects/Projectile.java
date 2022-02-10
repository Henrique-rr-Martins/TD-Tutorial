package objects;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Projectile {

    private Point2D.Float pos;
    private int id, projectileType, dmg;
    private float xSpeed, ySpeed, rotation;
    private boolean active = true;

    public Projectile(float x, float y, float xSpeed, float ySpeed, int dmg, float rotation, int id, int projectileType){
        this.pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.dmg = dmg;
        this.rotation = rotation;
        this.id = id;
        this.projectileType = projectileType;
    }

    public void move(){
        this.pos.x += xSpeed;
        this.pos.y += ySpeed;
    }





    public Point2D.Float getPos() { return pos; }
    public void setPos(Point2D.Float pos) { this.pos = pos; }
    public int getId() { return id; }
    public int getProjectileType() { return projectileType; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getDmg(){ return this.dmg; }
    public float getRotation(){ return this.rotation; }
}
