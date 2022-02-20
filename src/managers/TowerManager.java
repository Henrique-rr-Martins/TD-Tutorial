package managers;

import enemies.Enemy;
import objects.Tower;
import scenes.Playing;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static util.GlobalValuesUtil.*;
import static util.MathUtil.*;

public class TowerManager {
    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount = 0;

    private int spriteSize = GlobalValuesUtil.SPRITE_SIZE;

    public TowerManager(Playing playing) {
        this.playing = playing;
        this.loadTowerImgs();
    }

    private void loadTowerImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        this.towerImgs = new BufferedImage[GlobalValuesUtil.TOWER_AMOUNT];

        for (int i = 0; i < GlobalValuesUtil.TOWER_AMOUNT; i++)
            towerImgs[i] = atlas.getSubimage((4 + i) * spriteSize, spriteSize, spriteSize, spriteSize);
    }

    public void addTower(Tower selectedTower, int xPos, int yPos) {
        towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
    }

    public void update() {
        for(Tower t : towers) {
            t.update();
            this.attackEnemyIfClose(t);
        }
    }

    private void attackEnemyIfClose(Tower t) {
            for (Enemy e : this.playing.getEnemyManager().getEnemies()) {
                if (e.isAlive())
                    if (this.isEnemyInRange(t, e) && t.isCooldownOver()) {
                        this.playing.shootEnemy(t, e);
                        t.resetCooldown();
                    }
            }
    }

    private boolean isEnemyInRange(Tower t, Enemy e) {
        int range = getHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());

        return t.getRange() >= range;
    }

    public void draw(Graphics g) {
        for (Tower t : towers)
            g.drawImage(getTowerImgs()[t.getTowerType()], t.getX(), t.getY(), spriteSize, spriteSize, null);
    }

    public BufferedImage[] getTowerImgs() {
        return this.towerImgs;
    }

    public Tower getTowerAt(int x, int y) {
        for (Tower t : towers)
            if (t.getX() == x && t.getY() == y)
                return t;

        return null;
    }

    public void removeTower(Tower displayedTower) {
        for(int i = 0; i < towers.size(); i++){
            if(towers.get(i).getId() == displayedTower.getId()){
                towers.remove(i);
            }
        }
    }
}
