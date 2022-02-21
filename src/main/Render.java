package main;

import java.awt.*;

public class Render implements ImgEntitiesInterface{

    private Game game;

    public Render(Game game){
        this.game = game;
    }

    public void render(Graphics g){
        switch (GameStates.gameState){
            case MENU -> { this.game.getMenu().render(g); }
            case PLAYING -> { this.game.getPlaying().render(g); }
            case SETTINGS -> { this.game.getSettings().render(g); }
            case EDIT -> { this.game.getEditor().render(g); }
            case GAME_OVER -> { this.game.getGameOver().render(g); }
        }
    }
}
