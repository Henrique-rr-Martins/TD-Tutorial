package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

import static util.GlobalValuesUtil.*;
import static util.StringUtil.*;
import static main.GameStates.*;

public class GameOver extends GameScene implements SceneMethods{

    private MyButton bReplay, bMenu;

    public GameOver(Game game) {
        super(game);

        this.initbuttons();
    }

    private void initbuttons() {

        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;

        this.bMenu = new MyButton("Menu", x, y, w, h);
        this.bReplay = new MyButton("Replay", x, y + yOffset, w, h);
    }

    @Override
    public void render(Graphics g) {
        // game over text
        g.setFont(new Font("LucidaSans", Font.BOLD, 50));
        g.setColor(Color.RED);
        g.drawString(TXT_GAME_OVER, SCREEN_WIDTH / 2 - g.getFontMetrics().stringWidth(TXT_GAME_OVER) / 2, 100);

        g.setFont(new Font("LucidaSans", Font.BOLD, 15));
        bMenu.draw(g);
        bReplay.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x, y))
            setGameState(MENU);
        else if(bReplay.getBounds().contains(x, y))
            setGameState(PLAYING);
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bReplay.setMouseOver(false);

        if(bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if(bReplay.getBounds().contains(x, y))
            bReplay.setMouseOver(true);
    }

    @Override
    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else if(bReplay.getBounds().contains(x, y))
            bReplay.setMousePressed(true);
    }

    @Override
    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bReplay.resetBooleans();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }
}
