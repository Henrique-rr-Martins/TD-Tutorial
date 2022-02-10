package inputs;

import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameStates.*;

public class KeyboardListener implements KeyListener {

    private Game game;

    public KeyboardListener(Game game){
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (gameState){
            case MENU:
                this.keyPressedInMenuState(e);
                break;
            case PLAYING:
                this.keyPressedInPlayingState(e);
                break;
            case EDIT:
                this.keyPressedInEditState(e);
                break;
            case SETTINGS:
                this.keyPressedInSettingsState(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    private void keyPressedInMenuState(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameState = PLAYING;
        }
    }

    private void keyPressedInPlayingState(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE -> gameState = SETTINGS;
            default -> game.getPlaying().keyPressed(e);
        }
    }

    private void keyPressedInEditState(KeyEvent e){
        switch (e.getKeyCode()) {
            default:
                this.game.getEditor().keyPressed(e);
                break;
        }
    }

    private void keyPressedInSettingsState(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_BACK_SPACE -> gameState = PLAYING;
        }
    }
}
