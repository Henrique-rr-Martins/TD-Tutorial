package ui;

import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.MENU;
import static main.GameStates.setGameState;

public class BottomBar {

    private int x, y, width, height;
    private Playing playing;
    private MyButton bMenu, bSave;

    private Tile selectedTile;

    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    public BottomBar(int x, int y, int width, int height, Playing playing){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.playing = playing;
        // init buttons
        this.initButtons();
    }

    public void draw(Graphics g){
        // background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);
        // buttons
        this.drawButtons(g);
    }

    private void initButtons() {

        int squareSide = 50;
        int x = 20;
        int y = 640 + 100 / 4;

        int xOffset = (int) (squareSide * 1.3f);

        this.bMenu = new MyButton("Menu", x, y, squareSide, squareSide);
        this.bSave = new MyButton("Save", x + xOffset, y, squareSide, squareSide);

        for(int i = 0; i < playing.getTileManager().tiles.size(); i++){
            var tile = playing.getTileManager().tiles.get(i);

            tileButtons.add(new MyButton(tile.getName(), (x * 2 + xOffset * 2 + xOffset * i), y, squareSide, squareSide, i));
        }
    }

    private void drawButtons(Graphics g) {
        // menu button
        this.bMenu.draw(g);
        // save button
        this.bSave.draw(g);
        // tile buttons
        this.drawTileButtons(g);
        this.drawSelectedTile(g);
    }

    private void drawSelectedTile(Graphics g) {
        if(selectedTile != null){
            int x = 550;
            int y = 640 + 100 / 4;
            int squareSide = 50;
            g.drawImage(this.selectedTile.getSprite(), x, y, squareSide, squareSide, null);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, squareSide, squareSide);
        }
    }

    private void drawTileButtons(Graphics g) {

        for(MyButton b: tileButtons){
            // sprite
            g.drawImage(getButtImage(b.getId()), b.x, b.y, b.width, b.height, null);
            // mouse over
            if(b.isMouseOver()){
                g.setColor(Color.WHITE);
            }else{
                g.setColor(Color.BLACK);
            }
            // border
            g.drawRect(b.x, b.y, b.width, b.height);
            // mouse pressed
            if(b.isMousePressed()){
                g.drawRect(b.x + 1, b.y + 1, b.width - 1, b.height - 2);
                g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
            }
        }
    }

    public BufferedImage getButtImage(int id){
        return this.playing.getTileManager().getSprite((id));
    }

    public void mouseClicked(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMouseOver(false);
            setGameState(MENU);
        } else if(bSave.getBounds().contains(x, y)){
            this.saveLevel();
        } else {
            for(MyButton b : tileButtons){
                if(b.getBounds().contains(x, y)){
                    selectedTile = playing.getTileManager().getTile(b.getId());
                    playing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    private void saveLevel() {
        this.playing.saveLevel();
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bSave.setMouseOver(false);
        for(MyButton b : tileButtons)
            b.setMouseOver(false);

        if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if(bSave.getBounds().contains(x, y)){
            bSave.setMouseOver(true);
        } else {
            for(MyButton b : tileButtons){
                if(b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMousePressed(true);
        } else if(bSave.getBounds().contains(x, y)){
            bSave.setMousePressed(true);
        } else {
            for(MyButton b : tileButtons){
                if(b.getBounds().contains(x, y)){
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }

    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
        bSave.resetBooleans();
        for(MyButton b : tileButtons)
            b.resetBooleans();
    }
}
