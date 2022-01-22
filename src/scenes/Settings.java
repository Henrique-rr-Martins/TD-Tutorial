package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.*;

public class Settings extends GameScene implements SceneMethods{

    private MyButton bMenu;

    public Settings(Game game){
        super(game);

        this.initButtons();
    }

    private void initButtons() {

        int w = 150;
        int h = w / 3;
        int x = 3;
        int y = 4;

        this.bMenu = new MyButton("Menu", 3,4, w, h);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 640, 640);

        this.drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        this.bMenu.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            setGameState(MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMouseOver(false);
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
