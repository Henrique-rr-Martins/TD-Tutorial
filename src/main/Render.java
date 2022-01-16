package main;

import java.awt.*;

public class Render implements ImgEntitiesInterface{

    private Game game;

    public Render(Game game){
        this.game = game;
    }

    public void render(Graphics g){
        switch (GameStates.gameState){
            case MENU:
                this.game.getMenu().render(g);

                break;
            case PLAYING:
                this.game.getPlaying().render(g);

                break;
            case SETTINGS:
                this.game.getSettings().render(g);

                break;
            default:
        }
    }
}
