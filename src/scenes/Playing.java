package scenes;

import enemies.Enemy;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import objects.Tower;
import ui.ActionBar;
import util.GlobalValuesUtil;

import java.awt.*;
import java.awt.event.KeyEvent;

import static util.ConstantsUtil.Tiles.GRASS_TILE;
import static util.GlobalValuesUtil.*;

public class Playing extends GameScene implements SceneMethods{
    private ActionBar actionBar;
    private int mouseX, mouseY;

    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private ProjectileManager projectileManager;

    private Tower selectedTower;

    public Playing(Game game) {
        super(game);
        // bottom menu
        this.actionBar = new ActionBar(X_BAR_POSITION,
                Y_BAR_POSITION,
                BAR_WIDTH,
                BAR_HEIGHT,
                this);

        this.enemyManager = new EnemyManager(this);
        this.towerManager = new TowerManager(this);
        this.projectileManager = new ProjectileManager(this);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    public void update(){
        this.updateTick();
        this.enemyManager.update();
        this.towerManager.update();
        this.projectileManager.update();
    }

    @Override
    public void render(Graphics g) {
        this.drawLevel(g);
        this.enemyManager.draw(g);
        this.towerManager.draw(g);
        this.projectileManager.draw(g);
        this.drawSelectedTower(g);
        this.drawHighlight(g);
        this.actionBar.draw(g);
    }

    private void drawHighlight(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect(mouseX, mouseY, DEFAULT_MAP_TILE_SIZE, DEFAULT_MAP_TILE_SIZE);
    }

    private void drawSelectedTower(Graphics g) {
        if(selectedTower != null)
            g.drawImage(towerManager.getTowerImgs()[this.selectedTower.getTowerType()], mouseX, mouseY, null);
    }

    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;

        if(xCord < 0 || xCord > (SCREEN_WIDTH / 32 - 1))
            return 0;
        else if(yCord < 0 || yCord > (SCREEN_HEIGHT / 32 - 1))
            return 0;

        int id = lvl[yCord][xCord];

        return getGame().getTileManager().getTile(id).getTileType();
    }

    public void shootEnemy(Tower t, Enemy e){
        this.projectileManager.newProjectile(t, e);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640)
            this.actionBar.mouseClicked(x, y);
        else{
            if(selectedTower != null){
                if(this.isTileGrass() && this.getTowerAt(mouseX, mouseY) == null) {
                    this.towerManager.addTower(selectedTower, mouseX, mouseY);
                    this.selectedTower = null;
                }
            }else{
                Tower t = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(t);
            }
        }

    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640){
            this.actionBar.mouseMoved(x, y);
        } else{
            this.mouseX = (x / 32) * 32;
            this.mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= 640)
            this.actionBar.mousePressed(x, y);
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.actionBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {}

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.selectedTower = null;
        }
    }

    public TowerManager getTowerManager(){ return this.towerManager; }
    public void setSelectedTower(Tower selectedTower) { this.selectedTower = selectedTower; }
    private boolean isTileGrass() { return this.getTileType(mouseX, mouseY) == GRASS_TILE; }
    private Tower getTowerAt(int x, int y) { return towerManager.getTowerAt(mouseX, mouseY); }
    public EnemyManager getEnemyManager(){ return this.enemyManager; }
}
