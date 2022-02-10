package ui;

import objects.Tower;
import scenes.Playing;
import util.ConstantsUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.GameStates.MENU;
import static main.GameStates.setGameState;
import static util.GlobalValuesUtil.*;

public class ActionBar extends Bar {

    private Playing playing;
    private MyButton bMenu;

    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;

        this.initButtons();
    }

    private void initButtons() {

        this.bMenu = new MyButton("Menu", X_BUTTON_POSITION, Y_BUTTON_POSITION, BUTTON_SQUARE_SIDE, BUTTON_SQUARE_SIDE);

        this.towerButtons = new MyButton[TOWER_AMOUNT];

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", X_BUTTON_POSITION + BUTTON_X_OFFSET * (1 + i), Y_BUTTON_POSITION, BUTTON_SQUARE_SIDE, BUTTON_SQUARE_SIDE, i);
        }
    }

    private void drawButtons(Graphics g) {
        // menu button
        this.bMenu.draw(g);

        for (MyButton b : towerButtons) {
            int id = b.getId();
            BufferedImage img = playing.getTowerManager().getTowerImgs()[id];
            g.setColor(Color.GRAY);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(img, b.x, b.y, b.width, b.height, null);
            super.drawButtonFeedback(g, b);
        }
    }

    public void draw(Graphics g) {
        // background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);
        // buttons
        this.drawButtons(g);
        // display tower status
        this.drawDisplayedTower(g);
    }

    private void drawDisplayedTower(Graphics g) {
        if (displayedTower != null) {
            g.setColor(Color.GRAY);
            g.fillRect(X_TOWER_TILE_POSITION - 10, Y_TOWER_TILE_POSITION - 10, DATA_TOWER_DISPLAY_SIZE * 3 + 20, DATA_TOWER_DISPLAY_SIZE * 2);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()],
                    X_TOWER_TILE_POSITION,
                    Y_TOWER_TILE_POSITION,
                    DATA_TOWER_DISPLAY_SIZE,
                    DATA_TOWER_DISPLAY_SIZE,
                    null);
            g.setColor(Color.BLACK);
            g.drawRect(X_TOWER_TILE_POSITION - 10, Y_TOWER_TILE_POSITION - 10, DATA_TOWER_DISPLAY_SIZE * 3 + 20, DATA_TOWER_DISPLAY_SIZE * 2);
            g.drawRect(X_TOWER_TILE_POSITION, Y_TOWER_TILE_POSITION, DATA_TOWER_DISPLAY_SIZE, DATA_TOWER_DISPLAY_SIZE);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString("NAME: " + ConstantsUtil.Towers.getName(displayedTower.getTowerType()), X_TOWER_NAME_POSITION, Y_TOWER_NAME_POSITION);
            g.drawString("ID: " + displayedTower.getId(), X_TOWER_NAME_POSITION, Y_TOWER_ID_POSITION);
            
            this.drawDisplayedTowerBorder(g);
            this.drawDisplayedTowerRange(g);
        }
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawOval(displayedTower.getX() - ((int) displayedTower.getRange() - SPRITE_SIZE / 2),
                displayedTower.getY() - ((int) displayedTower.getRange() - SPRITE_SIZE / 2),
                (int) displayedTower.getRange() * 2,
                (int) displayedTower.getRange() * 2);
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(Color.CYAN);
        g.drawRect(displayedTower.getX(), displayedTower.getY(), SPRITE_SIZE, SPRITE_SIZE);
    }

    public void displayTower(Tower t) {
        displayedTower = t;
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(false);
            setGameState(MENU);
        } else {
            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    selectedTower = new Tower(0, 0, b.getId(), b.getId());
                    playing.setSelectedTower(selectedTower);
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for (MyButton b : towerButtons)
            b.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {
            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    return;
                }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else
            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y))
                    b.setMousePressed(true);
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        for (MyButton b : towerButtons)
            b.resetBooleans();
    }
}
