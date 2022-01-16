package inputs;

import main.Game;
import main.GameStates;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameStates.*;

public class KeyboardListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                System.out.println("W is pressed!");
                break;
            case KeyEvent.VK_D:
                System.out.println("D is pressed!");
                break;
            case KeyEvent.VK_S:
                System.out.println("S is pressed!");
                break;
            case KeyEvent.VK_A:
                System.out.println("A is pressed!");
                break;
            case KeyEvent.VK_ESCAPE:
                this.switchBetweenMenuAndPlaying();
                break;
            case KeyEvent.VK_BACK_SPACE:
                this.switchBetweenSettingsAndPlaying();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void switchBetweenMenuAndPlaying(){
        switch (gameState) {
            case PLAYING:
                gameState = MENU;
                break;
            case MENU:
                gameState = PLAYING;
                break;
            default:
                break;
        }
    }

    private void switchBetweenSettingsAndPlaying(){
        switch (gameState) {
            case PLAYING:
                gameState = SETTINGS;
                break;
            case SETTINGS:
                gameState = PLAYING;
                break;
            default:
                break;
        }
    }
}
