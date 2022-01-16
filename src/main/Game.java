package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;

import javax.swing.*;

public class Game extends JFrame implements Runnable{

    private GameScreen gameScreen;
    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    // BEGIN - Classes to render
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    // END - Classes to render

    public Game(){

        this.initClasses();
        // Add the Panel to the Frame
        super.add(gameScreen);
        // Pack() need to be call after all containers added to JFrame
        super.pack();
        // Default starting position of window - none
        super.setLocationRelativeTo(null);
        // Finish the operation
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Shows the screen
        super.setVisible(true);
    }

    private void start(){
        gameThread = new Thread(this){};
        gameThread.start();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initInputs();
        game.start();
    }

    private void initClasses(){
        this.render = new Render(this);
        // Create Panel to draw
        this.gameScreen = new GameScreen(this);
        this.menu = new Menu(this);
        this.playing = new Playing(this);
        this.settings = new Settings(this);
    }

    private void initInputs(){
        myMouseListener = new MyMouseListener();
        keyboardListener = new KeyboardListener();

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        super.requestFocus();
    }

    private void updateGame(){
//        System.out.println("Game Updated!");
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();

        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        while(true){
            now = System.nanoTime();
            // Render
            if(now - lastFrame >= timePerFrame){
                repaint();
                lastFrame = now;
                frames++;
            }
            // Update
            if(now - lastUpdate >= timePerUpdate){
                updateGame();
                lastUpdate = now;
                updates++;
            }

            if(System.currentTimeMillis() - lastTimeCheck >= 1000){
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }

    // Getters
    public Render getRender(){ return this.render; }
    public Menu getMenu() { return menu; }
    public Playing getPlaying() { return playing; }
    public Settings getSettings() { return settings; }
}
