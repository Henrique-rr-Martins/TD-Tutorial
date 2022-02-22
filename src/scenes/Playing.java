package scenes;

import enemies.Enemy;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.Tower;
import ui.ActionBar;

import java.awt.*;
import java.awt.event.KeyEvent;

import static util.ConstantsUtil.Tiles.GRASS_TILE;
import static util.GlobalValuesUtil.*;
import static main.GameStates.*;

public class Playing extends GameScene implements SceneMethods{
    private ActionBar actionBar;
    private int mouseX, mouseY;

    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private ProjectileManager projectileManager;
    private WaveManager waveManager;

    private Tower selectedTower;

    private int goldTick;
    private boolean gamePaused;

    public Playing(Game game) {
        super(game);
        // bottom menu
        this.actionBar = new ActionBar(X_BAR_POSITION,
                Y_BAR_POSITION,
                BAR_WIDTH,
                BAR_HEIGHT,
                this);

        this.enemyManager = new EnemyManager(this, start, end);
        this.towerManager = new TowerManager(this);
        this.projectileManager = new ProjectileManager(this);
        this.waveManager = new WaveManager(this);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    public void update(){
        if(!this.gamePaused) {
            this.updateTick();
            this.waveManager.update();

            // gold tick
            this.goldTick++;
            if (this.goldTick % (60 * 3) == 0)
                this.rewardPlayer(1);

            if (!this.waveManager.isThereMoreEnemiesInWave() && this.isAllEnemiesDead()) {
                if (this.isThereMoreWaves()) {
                    this.waveManager.startWaveTimer();
                    if (this.isWaveTimerOver()) {
                        this.waveManager.increaseWaveIndex();
                        this.enemyManager.getEnemies().clear();
                        this.waveManager.resetEnemyIndex();
                    }
                }
            }

            if (this.isTimeForNewEnemy()) {
                this.spawnEnemy();
            }

            this.enemyManager.update();
            this.towerManager.update();
            this.projectileManager.update();
        }
    }

    private boolean isAllEnemiesDead() {
        for(Enemy e : this.enemyManager.getEnemies())
            if(e.isAlive()) return false;

        return true;
    }

    @Override
    public void render(Graphics g) {
        this.drawLevel(g);
        this.actionBar.draw(g);
        this.enemyManager.draw(g);
        this.towerManager.draw(g);
        this.projectileManager.draw(g);

        this.drawSelectedTower(g);
        this.drawHighlight(g);
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

    private void spawnEnemy() {
        this.enemyManager.spawnEnemy(this.waveManager.getNextEnemy());
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640)
            this.actionBar.mouseClicked(x, y);
        else{
            if(selectedTower != null){
                if(this.isTileGrass() && this.getTowerAt(mouseX, mouseY) == null) {
                    this.towerManager.addTower(selectedTower, mouseX, mouseY);
                    this.removeGold(this.selectedTower.getTowerType());
                    this.selectedTower = null;
                }
            }else{
                Tower t = getTowerAt(mouseX, mouseY);
                actionBar.displayTower(t);
            }
        }

    }

    private void removeGold(int towerType) {
        this.actionBar.payForTower(towerType);
    }

    public void sellTower(Tower displayedTower) {
        this.towerManager.removeTower(displayedTower);
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

    public void rewardPlayer(int reward){ this.actionBar.addGold(reward); }
    public TowerManager getTowerManager(){ return this.towerManager; }
    public void setSelectedTower(Tower selectedTower) { this.selectedTower = selectedTower; }
    private boolean isTileGrass() { return this.getTileType(mouseX, mouseY) == GRASS_TILE; }
    private Tower getTowerAt(int x, int y) { return towerManager.getTowerAt(mouseX, mouseY); }
    public EnemyManager getEnemyManager(){ return this.enemyManager; }
    public WaveManager getWaveManager(){ return this.waveManager; }
    private boolean isTimeForNewEnemy() { return this.waveManager.isTimeForNewEnemy() && this.waveManager.isThereMoreEnemiesInWave(); }
    private boolean isThereMoreWaves() { return this.waveManager.isThereMoreWaves(); }
    private boolean isWaveTimerOver() { return this.waveManager.isWaveTimerOver(); }
    public void togglePause() { this.gamePaused = !gamePaused; }
    public boolean isGamePaused() { return this.gamePaused; }
    public void removeLife() { this.actionBar.removeLife(); }
    public void setGameStateToGameOver(){ setGameState(GAME_OVER); }
}
