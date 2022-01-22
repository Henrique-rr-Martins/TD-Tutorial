package scenes;

import main.Game;
import ui.MyButton;

import java.awt.*;

import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods{

    private MyButton bPlaying, bSettings, bQuit;

    public Menu(Game game) {
        super(game);
        // init buttons
        this.initButtons();
    }

    private void initButtons(){

        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;

        this.bPlaying = new MyButton("Play", x, y, w, h);
        this.bSettings = new MyButton("Settings", x, y + yOffset, w, h);
        this.bQuit = new MyButton("Quit", x, y + yOffset * 2, w, h);
    }

    @Override
    public void render(Graphics g) {
        this.drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        this.bPlaying.draw(g);
        this.bSettings.draw(g);
        this.bQuit.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bPlaying.getBounds().contains(x, y)){
            bPlaying.setMouseOver(false);
            setGameState(PLAYING);
        } else if (bSettings.getBounds().contains(x, y)){
            bSettings.setMouseOver(false);
            setGameState(SETTINGS);
        }else if (bQuit.getBounds().contains(x, y)){
            bQuit.setMouseOver(false);
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        // playing button
        bPlaying.setMouseOver(false);
        if(bPlaying.getBounds().contains(x, y)){
            bPlaying.setMouseOver(true);
        }
        // settings button
        bSettings.setMouseOver(false);
        if(bSettings.getBounds().contains(x, y)){
            bSettings.setMouseOver(true);
        }
        // quit button
        bQuit.setMouseOver(false);
        if(bQuit.getBounds().contains(x, y)){
            bQuit.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        // playing button
        if(bPlaying.getBounds().contains(x, y)){
            bPlaying.setMousePressed(true);
        }
        // setting button
        if(bSettings.getBounds().contains(x, y)){
            bSettings.setMousePressed(true);
        }
        // quit button
        if(bQuit.getBounds().contains(x, y)){
            bQuit.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) { this.resetButtons(); }

    private void resetButtons() {
        bPlaying.setMousePressed(false);
        bSettings.setMousePressed(false);
        bQuit.setMousePressed(false);
    }

    // method to import images
//    private void importImg(){
//        try(InputStream is = getClass().getResourceAsStream("/spriteatlas.png")){
//            this.img = ImageIO.read(is);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
