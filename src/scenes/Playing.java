package scenes;

import main.Game;
import managers.TileManager;
import ui.MyButton;
import util.LevelBuild;

import java.awt.*;

import static main.GameStates.*;

public class Playing extends GameScene implements SceneMethods{

    private int[][] lvl;
    private TileManager tileManager;

    private MyButton bMenu;

    public Playing(Game game) {
        super(game);

        // the level map
        this.lvl = LevelBuild.getLevelData();

        this.tileManager = new TileManager();

        // init buttons
        this.initButtons();
    }

    private void initButtons() {

        int w = 150;
        int h = w / 3;
        int x = 3;
        int y = 4;

        this.bMenu = new MyButton("Menu", x, y, w, h);
    }

    @Override
    public void render(Graphics g) {

        for (int y = 0; y < lvl.length; y++) {
            for (int x = 0; x < lvl[y].length; x++) {
                int id = lvl[y][x];
                g.drawImage(this.tileManager.getSprite(id), x * 32, y * 32, null);
            }
        }

        this.drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        this.bMenu.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMouseOver(false);
            setGameState(MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.resetButtons();
    }

    private void resetButtons(){
        this.bMenu.setMousePressed(false);
    }
}
