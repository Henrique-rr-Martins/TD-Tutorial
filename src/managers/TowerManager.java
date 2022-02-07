package managers;

import objects.Tower;
import scenes.Playing;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static util.ConstantsUtil.Towers.*;

public class TowerManager {
    private Playing playing;
    private BufferedImage[] towerImgs;
    private Tower tower;

    public TowerManager(Playing playing){
        this.playing = playing;

        this.loadTowerImgs();
        this.initTowers();
    }

    private void initTowers() {
        tower = new Tower(3*GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE,
                6*GlobalValuesUtil.DEFAULT_MAP_TILE_SIZE,
                0,
                ARCHER);
    }

    private void loadTowerImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        this.towerImgs = new BufferedImage[GlobalValuesUtil.TOWER_IMAGES_AMOUNT];

        for(int i = 0; i < GlobalValuesUtil.TOWER_IMAGES_AMOUNT; i++)
            towerImgs[i] = atlas.getSubimage((4+i) * GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE);
    }

    public void update(){

    }

    public void render(Graphics g){
        this.draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(towerImgs[ARCHER],
                tower.getX(),
                tower.getY(),
                null);
    }
}
