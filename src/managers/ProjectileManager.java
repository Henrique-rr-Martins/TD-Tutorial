package managers;

import enemies.Enemy;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static util.ConstantsUtil.Projectiles.*;
import static util.ConstantsUtil.Towers.*;
import static util.GlobalValuesUtil.PROJECTILE_AMOUNT;
import static util.GlobalValuesUtil.SPRITE_SIZE;
import static java.lang.Math.*;

public class ProjectileManager {

    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] projImages;
    private int projectileId = 0;

    public ProjectileManager(Playing playing){
        this.playing = playing;
        this.projImages = new BufferedImage[PROJECTILE_AMOUNT];
        this.importImages();
    }

    private void importImages(){
        // x 7 8 9 y 1
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        for(int i = 0; i < 3; i++)
            projImages[i] = atlas.getSubimage((7 + i) * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
    }

    public void newProjectile(Tower t, Enemy e){
        int projectileType = this.getProjectileType(t);

        int xDist = (int) (t.getX() - e.getX());
        int yDist = (int) (t.getY() - e.getY());
        int totalDist = abs(xDist) + abs(yDist);

        float xPercent =  (float) abs(xDist) / totalDist;

        float xSpeed = xPercent * getSpeed(projectileType);
        float ySpeed = getSpeed(projectileType) - xSpeed;

        if(t.getX() > e.getX())
            xSpeed *= -1;
        if(t.getY() > e.getY())
            ySpeed *= -1;

        float arcValue = (float) atan((float) yDist / xDist);
        float rotate = (float) toDegrees(arcValue);

        if(xDist < 0)
            rotate += 180;

        projectiles.add(new Projectile(t.getX() + (float) SPRITE_SIZE/2, t.getY() + (float) SPRITE_SIZE/2,
                xSpeed, ySpeed, t.getDmg(), rotate, projectileId++, projectileType));
    }

    public void update(){
        ArrayList<Projectile> projectilesToDestroy = new ArrayList<>();

        for(Projectile p : projectiles)
            if(p.isActive()) {
                p.move();
                if(this.isProjectileHittingEnemy(p)) {
                    p.setActive(false);
                    projectilesToDestroy.add(p);
                }
            }else
                projectilesToDestroy.add(p);

        for(Projectile p : projectilesToDestroy)
            this.projectiles.remove(p);
    }

    private boolean isProjectileHittingEnemy(Projectile p) {
        for(Enemy e : playing.getEnemyManager().getEnemies()){
            if(e.getBounds().contains(p.getPos())){
                e.hurt(p.getDmg());

                return true;
            }
        }

        return false;
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        for(Projectile p : projectiles)
            if(p.isActive()) {
                g2d.translate(p.getPos().x, p.getPos().y);
                g2d.rotate(toRadians(p.getRotation()));
                g2d.drawImage(this.projImages[p.getProjectileType()], -SPRITE_SIZE/2, -SPRITE_SIZE/2, null);
                g2d.rotate(-toRadians(p.getRotation()));
                g2d.translate(-p.getPos().x, -p.getPos().y);
            }
    }

    private int getProjectileType(Tower t) {
        return switch (t.getTowerType()){
            case ARCHER -> ARROW;
            case CANNON -> BOMB;
            case WIZARD -> CHAINS;
            default -> -1;
        };
    }
}
