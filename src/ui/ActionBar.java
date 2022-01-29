package ui;

import scenes.Playing;
import util.GlobalValuesUtil;

import java.awt.*;

import static main.GameStates.MENU;
import static main.GameStates.setGameState;

public class ActionBar extends Bar {

    private Playing playing;
    private MyButton bMenu;

    public ActionBar(int x, int y, int width, int height, Playing playing){
        super(x, y, width, height);
        this.playing = playing;
        this.bMenu = new MyButton("Menu",
                GlobalValuesUtil.X_BUTTON_POSITION,
                GlobalValuesUtil.Y_BUTTON_POSITION,
                GlobalValuesUtil.BUTTON_SQUARE_SIDE,
                GlobalValuesUtil.BUTTON_SQUARE_SIDE);
    }

    public void draw(Graphics g){
        // background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);
        // buttons
        this.drawButtons(g);
    }

    private void initButtons() {

        int squareSide = 50;
        int x = 20;
        int y = 640 + 100 / 4;

        int xOffset = (int) (squareSide * 1.3f);

        this.bMenu = new MyButton("Menu", x, y, squareSide, squareSide);
    }

    private void drawButtons(Graphics g) {
        // menu button
        this.bMenu.draw(g);
    }

    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMouseOver(false);
            setGameState(MENU);
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        }
    }

    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMousePressed(true);
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
    }
}
