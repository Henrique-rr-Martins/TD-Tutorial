package scenes;

import main.Game;
import managers.TileManager;
import objects.Tile;
import ui.BottomBar;
import util.LevelBuild;
import util.LoadSave;

import java.awt.*;

public class Playing extends GameScene implements SceneMethods{

    private int[][] lvl;
    private TileManager tileManager;
    private Tile selectedTile;
    private BottomBar bottomBar;
    private int mouseX, mouseY;
    private boolean drawSelect = false;

    public Playing(Game game) {
        super(game);
        // the level map
        this.lvl = LevelBuild.getLevelData();
        this.tileManager = new TileManager();
        // bottom menu
        this.bottomBar = new BottomBar(0, 640, 640, 100, this);

        this.createDefaultLevel();
        this.loadDefaultLevel();
    }

    public void saveLevel() {
        LoadSave.saveLevel("new_level", lvl);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
    }

    private void createDefaultLevel() {
        int[] arr = new int[400];

        for(int i = 0; i < arr.length; i++)
            arr[i] = 0;

        LoadSave.createLevel("new_level",arr);
    }

    @Override
    public void render(Graphics g) {

        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                g.drawImage(this.tileManager.getSprite(id), x * 32, y * 32, null);
            }
        }
        this.bottomBar.draw(g);
        this.drawSelectedTile(g);
    }

    private void drawSelectedTile(Graphics g) {
        if(this.selectedTile != null && this.drawSelect){
            g.drawImage(this.selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void setSelectedTile(Tile tile){
        this.selectedTile = tile;
    }

    public TileManager getTileManager(){ return this.tileManager; }

    @Override
    public void mouseClicked(int x, int y) {

        if(y >= 640){
            this.bottomBar.mouseClicked(x, y);
        }else{
            this.changeTile(x, y);
        }
    }

    private void changeTile(int x, int y) {
        if(this.drawSelect && this.selectedTile != null) {
            int tileX = x / 32;
            int tileY = y / 32;

            if(this.lvl[tileY][tileX] == this.selectedTile.getId())
                return;

            this.lvl[tileY][tileX] = this.selectedTile.getId();
        }
    }

    @Override
    public void mouseMoved(int x, int y) {

        if(y >= 640){
            this.bottomBar.mouseMoved(x, y);
            this.drawSelect = false;
        } else{
            this.mouseX = (x / 32) * 32;
            this.mouseY = (y / 32) * 32;

            this.drawSelect = true;
        }
    }

    @Override
    public void mousePressed(int x, int y) {

        if(y >= 640){
            this.bottomBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.bottomBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
        if(y >= 640){

        }else{
            this.changeTile(x, y);
        }
    }
}
