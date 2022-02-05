package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import util.GlobalValuesUtil;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel{

    private Game game;
    private Dimension size;

    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    public GameScreen(Game game){
        this.game = game;
        this.setPanelSize();
    }

    public void initInputs(){
        myMouseListener = new MyMouseListener(this.game);
        keyboardListener = new KeyboardListener(game);

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        super.requestFocus();
    }

    private void setPanelSize(){
        this.size = new Dimension(GlobalValuesUtil.PANEL_WIDTH, GlobalValuesUtil.PANEL_HEIGHT);
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
