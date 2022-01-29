package scenes;

import main.Game;
import ui.ActionBar;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Playing extends GameScene implements SceneMethods{

    private int[][] lvl;
    private ActionBar bottomBar;
    private int mouseX, mouseY;

    public Playing(Game game) {
        super(game);

        this.loadDefaultLevel();
        // bottom menu
        this.bottomBar = new ActionBar(GlobalValuesUtil.X_BAR_POSITION,
                GlobalValuesUtil.Y_BAR_POSITION,
                GlobalValuesUtil.BAR_WIDTH,
                GlobalValuesUtil.BAR_HEIGHT,
                this);

    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new_level");
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    @Override
    public void render(Graphics g) {

        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                g.drawImage(this.getSprite(id), x * 32, y * 32, null);
            }
        }
        this.bottomBar.draw(g);
    }

    private BufferedImage getSprite(int spriteId){ return this.getGame().getTileManager().getSprite(spriteId); }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640)
            this.bottomBar.mouseClicked(x, y);
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y >= 640){
            this.bottomBar.mouseMoved(x, y);
        } else{
            this.mouseX = (x / 32) * 32;
            this.mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y >= 640)
            this.bottomBar.mousePressed(x, y);
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.bottomBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
