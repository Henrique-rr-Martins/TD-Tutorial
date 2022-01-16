package main;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel{

    private Game game;
    private Dimension size;

    public GameScreen(Game game){
        this.game = game;
        this.setPanelSize();
    }

    private void setPanelSize(){
        this.size = new Dimension(640, 640);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    /**
     *  Use the index of sprites variable to instantiate a Sprite in the Panel.
     *  You don't need to do anything, it's abstracted by the JPanel class which is extended by this class.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        this.game.getRender().render(g);
    }
}
