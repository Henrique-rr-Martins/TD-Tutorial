package scenes;

import main.Game;
import objects.Tile;
import ui.ToolBar;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Editing extends GameScene implements SceneMethods {

    private int[][] lvl;
    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelect = false;
    private final ToolBar toolBar;

    public Editing(Game game) {

        super(game);
        this.loadDefaultLevel();
        this.toolBar = new ToolBar(GlobalValuesUtil.X_BAR_POSITION,
                GlobalValuesUtil.Y_BAR_POSITION,
                GlobalValuesUtil.BAR_WIDTH,
                GlobalValuesUtil.BAR_HEIGHT,
                this);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
    }

    @Override
    public void render(Graphics g) {
        this.drawLevel(g);
        this.toolBar.draw(g);
        this.drawSelectedTile(g);
    }

    private void drawLevel(Graphics g){
        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                g.drawImage(this.getSprite(id), x * 32, y * 32, null);
            }
        }
    }

    private BufferedImage getSprite(int spriteId){ return this.getGame().getTileManager().getSprite(spriteId); }

    private void drawSelectedTile(Graphics g) {
        if(this.selectedTile != null && this.drawSelect){
            g.drawImage(this.selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void saveLevel() {
        LoadSave.saveLevel("new_level", lvl);
        getGame().getPlaying().setLevel(lvl);
    }

    public void setSelectedTile(Tile tile){
        this.selectedTile = tile;
    }

    private void changeTile(int x, int y) {
        if(this.drawSelect && this.selectedTile != null) {
            int tileX = x / 32;
            int tileY = y / 32;

            if(this.lvl[tileY][tileX] != this.selectedTile.getId())
                this.lvl[tileY][tileX] = this.selectedTile.getId();
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640){
            this.toolBar.mouseClicked(x, y);
        }else{
            this.changeTile(x, y);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640){
            this.toolBar.mouseMoved(x, y);
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
            this.toolBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {

    }

    @Override
    public void mouseDragged(int x, int y) {
        if(y < 640)
            this.changeTile(x, y);
    }
}
