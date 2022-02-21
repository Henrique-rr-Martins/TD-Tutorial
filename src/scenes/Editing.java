package scenes;

import main.Game;
import objects.PathPoint;
import objects.Tile;
import ui.ToolBar;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import static util.ConstantsUtil.Tiles.*;

public class Editing extends GameScene implements SceneMethods {

    private Tile selectedTile;
    private int mouseX, mouseY;
    private boolean drawSelect = false;
    private final ToolBar toolBar;

    public Editing(Game game) {

        super(game);
        this.toolBar = new ToolBar(GlobalValuesUtil.X_BAR_POSITION,
                GlobalValuesUtil.Y_BAR_POSITION,
                GlobalValuesUtil.BAR_WIDTH,
                GlobalValuesUtil.BAR_HEIGHT,
                this);
    }

    public void update(){
        this.updateTick();
    }

    @Override
    public void render(Graphics g) {
        this.drawLevel(g);
        this.toolBar.draw(g);
        this.drawSelectedTile(g);
        this.drawPathPoints(g);
    }

    private void drawPathPoints(Graphics g) {
        if(start != null){
            g.drawImage(toolBar.getPathStart(),
                    start.getXCord() * GlobalValuesUtil.SPRITE_SIZE,
                    start.getYCord() * GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE,
                    null);
        }

        if(end != null){
            g.drawImage(toolBar.getPathEnd(),
                    end.getXCord() * GlobalValuesUtil.SPRITE_SIZE,
                    end.getYCord() * GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE,
                    GlobalValuesUtil.SPRITE_SIZE,
                    null);
        }

    }

    private void drawSelectedTile(Graphics g) {
        if(this.selectedTile != null && this.drawSelect){
            g.drawImage(this.selectedTile.getSprite(), mouseX, mouseY, GlobalValuesUtil.SPRITE_SIZE, GlobalValuesUtil.SPRITE_SIZE, null);
        }
    }

    public void saveLevel() {
        LoadSave.saveLevel("new_level", lvl, start, end);
        getGame().resetPlayingClass();
        getGame().getPlaying().setLevel(lvl);
    }

    public void setSelectedTile(Tile tile){
        this.selectedTile = tile;
    }

    private void changeTile(int x, int y) {
        if(this.drawSelect && this.selectedTile != null) {
            int tileX = x / GlobalValuesUtil.SPRITE_SIZE;
            int tileY = y / GlobalValuesUtil.SPRITE_SIZE;

            if(selectedTile.getId() >= 0) {

                if (this.lvl[tileY][tileX] != this.selectedTile.getId())
                    this.lvl[tileY][tileX] = this.selectedTile.getId();
            }else{
                int id = lvl[tileY][tileX];
                if(super.getGame().getTileManager().getTile(id).getTileType() == ROAD_TILE){
                    if(selectedTile.getId() == -1)
                        start = new PathPoint(tileX, tileY);
                    if(selectedTile.getId() == -2 )
                        end = new PathPoint(tileX, tileY);
                }
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= GlobalValuesUtil.SCREEN_HEIGHT){
            this.toolBar.mouseClicked(x, y);
        }else{
            this.changeTile(x, y);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= GlobalValuesUtil.SCREEN_HEIGHT){
            this.toolBar.mouseMoved(x, y);
            this.drawSelect = false;
        } else{
            this.mouseX = (x / GlobalValuesUtil.SPRITE_SIZE) * GlobalValuesUtil.SPRITE_SIZE;
            this.mouseY = (y / GlobalValuesUtil.SPRITE_SIZE) * GlobalValuesUtil.SPRITE_SIZE;

            this.drawSelect = true;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= GlobalValuesUtil.SCREEN_HEIGHT)
            this.toolBar.mousePressed(x, y);
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.toolBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
        if(y < GlobalValuesUtil.SCREEN_HEIGHT)
            this.changeTile(x, y);
    }

    public void keyPressed(KeyEvent e){

        if(e.getKeyCode() == KeyEvent.VK_R)
            this.toolBar.rotateSprite();
    }
}
