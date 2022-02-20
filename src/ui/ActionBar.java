package ui;

import objects.Tower;
import scenes.Playing;
import util.ConstantsUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import static main.GameStates.MENU;
import static main.GameStates.setGameState;
import static util.GlobalValuesUtil.*;
import static util.ConstantsUtil.Towers.*;

public class ActionBar extends Bar {

    private Playing playing;
    private MyButton bMenu;

    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;
    private MyButton bSellTower, bUpgradeTower;

    private DecimalFormat formatter;

    private int gold = 100;
    private boolean showTowerCost;
    private int towerCostType;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;

        this.formatter = new DecimalFormat("0.0");

        this.initButtons();
    }

    private void initButtons() {

        this.bMenu = new MyButton("Menu", X_BUTTON_POSITION, Y_BUTTON_POSITION, BUTTON_SQUARE_SIDE, BUTTON_SQUARE_SIDE);

        this.towerButtons = new MyButton[TOWER_AMOUNT];

        for (int i = 0; i < towerButtons.length; i++) {
            towerButtons[i] = new MyButton("", X_BUTTON_POSITION + BUTTON_X_OFFSET * (1 + i), Y_BUTTON_POSITION, BUTTON_SQUARE_SIDE, BUTTON_SQUARE_SIDE, i);
        }

        this.bSellTower = new MyButton("SELL", X_TOWER_TILE_POSITION, Y_TOWER_TILE_POSITION + DATA_TOWER_DISPLAY_SIZE + 17, 50, 20);
        this.bUpgradeTower = new MyButton("UPGRADE", X_TOWER_TILE_POSITION + 80, Y_TOWER_TILE_POSITION + DATA_TOWER_DISPLAY_SIZE + 17, 70, 20);
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
        // wave info
        this.drawWaveInfo(g);
        // gold info
        this.drawGoldAmount(g);
        // draw tower cost
        if(this.showTowerCost)
            this.drawTowerCost(g);
    }

    private void drawTowerCost(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(280, Y_BUTTON_POSITION, 120, BUTTON_SQUARE_SIDE);
        g.setColor(Color.BLACK);
        g.drawRect(280, Y_BUTTON_POSITION, 120, BUTTON_SQUARE_SIDE);

        g.drawString(getName(this.towerCostType), 285, Y_BUTTON_POSITION + 20);
        g.drawString("Cost: " + getCost(towerCostType) + "g", 285, Y_BUTTON_POSITION + 35);

        // show if player lacks gold for the selected tower
        if(!this.isGoldEnoughForTower(this.towerCostType)){
            g.setColor(Color.RED);
            var fontMetrics = g.getFontMetrics();
            g.drawString("Can't afford", 285, Y_BUTTON_POSITION + BUTTON_Y_OFFSET + fontMetrics.getHeight());

            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(282, Y_BUTTON_POSITION + BUTTON_SQUARE_SIDE - 1, 280  + 120, Y_BUTTON_POSITION + 1);
        }
    }

    private void drawGoldAmount(Graphics g) {
        var fontMetrics = g.getFontMetrics();
        g.drawString("gold: " + this.gold, X_BUTTON_POSITION + BUTTON_X_OFFSET, Y_BUTTON_POSITION + BUTTON_Y_OFFSET + fontMetrics.getHeight());
    }

    private void drawWaveInfo(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("LucidaSans", Font.BOLD, 15));

        this.drawWaveTimerInfo(g);
        this.drawEnemiesLeftInfo(g);
        this.drawWavesLeftInfo(g);
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = this.playing.getWaveManager().getWaveIndex();
        int size = this.playing.getWaveManager().getWaves().size();
        g.drawString("Wave " + (current +1) + " / " + size, X_TOWER_TILE_POSITION, BUTTON_Y_OFFSET + 670);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        int remaining = this.playing.getEnemyManager().getAmountOfAliveEnemies();
        int waveSize = this.playing.getWaveManager().getWaves().get(this.playing.getWaveManager().getWaveIndex()).getEnemyList().size();
        g.drawString("Enemies left: " + remaining + " / " + waveSize, X_TOWER_TILE_POSITION, BUTTON_Y_OFFSET + 690);
    }

    private void drawWaveTimerInfo(Graphics g){
//        if(this.playing.getWaveManager().isWaveTimerStarted()){
            float timeLeft = this.playing.getWaveManager().getTimeLeft();
            String formattedText = this.formatter.format(timeLeft);

            g.drawString("Time left: " + formattedText, X_TOWER_TILE_POSITION, BUTTON_Y_OFFSET + 710);
//        }
    }

    private void drawDisplayedTower(Graphics g) {
        if (displayedTower != null) {
            g.setColor(Color.GRAY);
            g.fillRect(X_TOWER_TILE_POSITION - 10, Y_TOWER_TILE_POSITION, DATA_TOWER_DISPLAY_SIZE * 5 + 20, DATA_TOWER_DISPLAY_SIZE * 2 + 10);
            g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()],
                    X_TOWER_TILE_POSITION,
                    Y_TOWER_TILE_POSITION + 7,
                    DATA_TOWER_DISPLAY_SIZE + 5,
                    DATA_TOWER_DISPLAY_SIZE + 5,
                    null);
            g.setColor(Color.BLACK);
            g.drawRect(X_TOWER_TILE_POSITION - 10, Y_TOWER_TILE_POSITION, DATA_TOWER_DISPLAY_SIZE * 5 + 20, DATA_TOWER_DISPLAY_SIZE * 2 + 10);
            g.drawRect(X_TOWER_TILE_POSITION, Y_TOWER_TILE_POSITION + 10, DATA_TOWER_DISPLAY_SIZE + 5, DATA_TOWER_DISPLAY_SIZE + 5);
            g.setFont(new Font("LucidaSans", Font.BOLD, 12));
            g.drawString("NAME: " + ConstantsUtil.Towers.getName(displayedTower.getTowerType()), X_TOWER_NAME_POSITION, Y_TOWER_NAME_POSITION);
            g.drawString("ID: " + displayedTower.getId(), X_TOWER_NAME_POSITION, Y_TOWER_ID_POSITION + 3);
            g.drawString("TIER: " + displayedTower.getTier(), X_TOWER_NAME_POSITION + g.getFontMetrics().stringWidth("ID: " + displayedTower.getId()) + 20, Y_TOWER_ID_POSITION + 3);
            
            this.drawDisplayedTowerBorder(g);
            this.drawDisplayedTowerRange(g);

            // sell button
            this.bSellTower.draw(g);
            this.drawButtonFeedback(g, bSellTower);
            //upgrade button
            if(this.displayedTower != null & this.displayedTower.getTier() < 3) {
                this.bUpgradeTower.draw(g);
                this.drawButtonFeedback(g, bUpgradeTower);
            }

            if(this.bSellTower.isMouseOver()){
                g.setColor(Color.RED);
                g.drawString("Sell for: " + this.getSellAmount(this.displayedTower) + "g", X_TOWER_NAME_POSITION, Y_TOWER_ID_POSITION + 14);
            }else if(this.bUpgradeTower.isMouseOver()){
                g.setColor(Color.BLUE);
                g.drawString("Upgrade for: " + this.getUpgradeAmount(this.displayedTower) + "g", X_TOWER_NAME_POSITION, Y_TOWER_ID_POSITION + 14);
            }
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

    private void sellTowerClicked() {
        this.gold += this.getSellAmount(this.displayedTower);
        this.playing.sellTower(this.displayedTower);
        this.displayedTower = null;
    }

    private void upgradeTowerClicked() {
        if(this.gold >= this.getUpgradeAmount(this.displayedTower)) {
            this.displayedTower.upgradeTower();
            gold -= this.getUpgradeAmount(this.displayedTower);
        }
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(false);
            setGameState(MENU);
        } else {
            if(this.displayedTower != null){
                if(this.bSellTower.getBounds().contains(x, y)){
                    this.sellTowerClicked();
                    return;
                }else if(this.bUpgradeTower.getBounds().contains(x, y)){
                    this.upgradeTowerClicked();
                    return;
                }
            }

            for (MyButton b : towerButtons) {
                if (b.getBounds().contains(x, y)) {
                    if(this.isGoldEnoughForTower(b.getId())){
                        selectedTower = new Tower(0, 0, b.getId(), b.getId());
                        playing.setSelectedTower(selectedTower);
                    }else{

                    }
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        this.showTowerCost = false;
        bSellTower.setMouseOver(false);
        bUpgradeTower.setMouseOver(false);
        for (MyButton b : towerButtons)
            b.setMouseOver(false);

        this.bSellTower.setMouseOver(false);
        this.bSellTower.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else {

            if(this.displayedTower != null){
                if(this.bSellTower.getBounds().contains(x, y)){
                    this.bSellTower.setMouseOver(true);
                    return;
                }else if(this.bUpgradeTower.getBounds().contains(x, y) && this.displayedTower.getTier() < 3){
                    this.bUpgradeTower.setMouseOver(true);
                    return;
                }
            }
            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    this.showTowerCost = true;
                    this.towerCostType = b.getId();
                    return;
                }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else {

            if(this.displayedTower != null){
                if(this.bSellTower.getBounds().contains(x, y)){
                    this.bSellTower.setMousePressed(true);
                    return;
                }else if(this.bUpgradeTower.getBounds().contains(x, y) && this.displayedTower.getTier() < 3){
                    this.bUpgradeTower.setMousePressed(true);
                    return;
                }
            }
            for (MyButton b : towerButtons)
                if (b.getBounds().contains(x, y))
                    b.setMousePressed(true);
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bSellTower.resetBooleans();
        bUpgradeTower.resetBooleans();
        for (MyButton b : towerButtons)
            b.resetBooleans();
    }

    public void payForTower(int towerCostType){
        this.gold -= getCost(towerCostType);
    }

    private boolean isGoldEnoughForTower(int towerCostType) {
        return this.gold >= getCost(towerCostType);
    }

    public void addGold(int reward) {
        this.gold += reward;
    }

    private int getUpgradeAmount(Tower displayedTower) { return (int) (getCost(displayedTower.getTowerType()) * 0.3f); }
    private int getSellAmount(Tower displayedTower) {
        int upgradeCost = (this.displayedTower.getTier() - 1) * getUpgradeAmount(this.displayedTower);
        upgradeCost *= 0.5f;

        return getCost(displayedTower.getTowerType()) / 2 + upgradeCost;
    }
}
