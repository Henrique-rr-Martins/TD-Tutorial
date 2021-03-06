package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;
import managers.TileManager;
import scenes.*;
import util.LoadSave;

import javax.swing.*;

import static util.StringUtil.*;

public class Game extends JFrame implements Runnable {

	private GameScreen gameScreen;
	private Thread gameThread;

	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;

	// BEGIN - Classes to render
	private TileManager tileManager;
	private Render render;
	private Menu menu;
	private Playing playing;
	private Settings settings;
	private Editing editing;
	private GameOver gameOver;
	// END - Classes to render

	public Game() {
		// if the folder doesn't exist, create it
		LoadSave.createFolder();

		// create default level
		this.createDefaultLevel();

		this.initClasses();

		// Finish the operation
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Default starting position of window - none
		super.setLocationRelativeTo(null);
		super.setResizable(false);
		// title
		super.setTitle("Tower Defense");
		// Add the Panel to the Frame
		super.add(gameScreen);
		// Pack() need to be call after all containers added to JFrame
		super.pack();
		// Shows the screen
		super.setVisible(true);
		super.setLocationRelativeTo(null);
	}

	private void createDefaultLevel() {
		int[] arr = new int[400];
		// Arrays.fill(arr, 0);
		LoadSave.createLevel(arr);
	}

	private void start() {
		gameThread = new Thread(this) {
		};
		gameThread.start();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.gameScreen.initInputs();
		game.start();
	}

	private void initClasses() {
		this.tileManager = new TileManager();
		this.render = new Render(this);
		// Create Panel to draw
		this.gameScreen = new GameScreen(this);
		this.menu = new Menu(this);
		this.playing = new Playing(this);
		this.settings = new Settings(this);
		this.editing = new Editing(this);
		this.gameOver = new GameOver(this);
	}

	private void updateGame() {
		switch (GameStates.gameState) {
		case PLAYING:
			playing.update();
			break;
		case MENU:
			break;
		case SETTINGS:
			break;
		case EDIT:
			editing.update();
		}
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

		while (true) {
			now = System.nanoTime();
			// Render
			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				frames++;
			}
			// Update
			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}

			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
				this.playing.setFpsAndUps(String.format(FPS_UPD_INFOS, frames, updates));
				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}
		}
	}

	// Getters
	public Render getRender() {
		return this.render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Settings getSettings() {
		return settings;
	}

	public Editing getEditor() {
		return editing;
	}

	public GameOver getGameOver() {
		return this.gameOver;
	}

	public TileManager getTileManager() {
		return this.tileManager;
	}

	public void resetPlayingClass() {
		this.playing = new Playing(this);
	}
}
