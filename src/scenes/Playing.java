package scenes;

import main.Game;
import managers.EnemyManager;
import ui.ActionBar;
import util.GlobalValuesUtil;

import java.awt.*;

import static util.ConstantsUtil.Enemies.*;

public class Playing extends GameScene implements SceneMethods{
    private ActionBar bottomBar;
    private int mouseX, mouseY;

    private EnemyManager enemyManager;

    public Playing(Game game) {
        super(game);
        // bottom menu
        this.bottomBar = new ActionBar(GlobalValuesUtil.X_BAR_POSITION,
                GlobalValuesUtil.Y_BAR_POSITION,
                GlobalValuesUtil.BAR_WIDTH,
                GlobalValuesUtil.BAR_HEIGHT,
                this);

        this.enemyManager = new EnemyManager(this);
    }

    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    public void update(){
        this.updateTick();
        this.enemyManager.update();
    }

    @Override
    public void render(Graphics g) {
        this.drawLevel(g);
        this.bottomBar.draw(g);
        this.enemyManager.draw(g);
    }

    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = x / 32;

        try{
            if(xCord < 0 || xCord > (GlobalValuesUtil.SCREEN_WIDTH / 32 - 1))
                return 0;
            else if(yCord < 0 || yCord > (GlobalValuesUtil.SCREEN_HEIGHT / 32 - 1))
                return 0;

            int id = lvl[y / 32][x / 32];
            return getGame().getTileManager().getTile(id).getTileType();
        } catch(ArrayIndexOutOfBoundsException e){
            return 0;
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y >= 640)
            this.bottomBar.mouseClicked(x, y);
        else
            this.enemyManager.addEnemy(ORC);
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
