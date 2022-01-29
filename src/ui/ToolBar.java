package ui;

import objects.Tile;
import scenes.Editing;
import util.GlobalValuesUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.MENU;
import static main.GameStates.setGameState;

public class ToolBar extends Bar {

    private Tile selectedTile;
    private Editing editing;
    private MyButton bMenu, bSave;
    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        this.initButtons();
    }

    private void initButtons() {
        this.bMenu = new MyButton("Menu",
                GlobalValuesUtil.X_BUTTON_POSITION,
                GlobalValuesUtil.Y_BUTTON_POSITION,
                GlobalValuesUtil.BUTTON_SQUARE_SIDE,
                GlobalValuesUtil.BUTTON_SQUARE_SIDE);
        this.bSave = new MyButton("Save",
                GlobalValuesUtil.X_BUTTON_POSITION + GlobalValuesUtil.BUTTON_X_OFFSET,
                GlobalValuesUtil.Y_BUTTON_POSITION,
                GlobalValuesUtil.BUTTON_SQUARE_SIDE,
                GlobalValuesUtil.BUTTON_SQUARE_SIDE);

        for (int i = 0; i < editing.getGame().getTileManager().tiles.size(); i++) {
            var tile = editing.getGame().getTileManager().tiles.get(i);
            
            tileButtons.add(new MyButton(tile.getName(),
                    (GlobalValuesUtil.X_BUTTON_POSITION * 2 + GlobalValuesUtil.BUTTON_X_OFFSET * 2 + GlobalValuesUtil.BUTTON_X_OFFSET * i),
                    GlobalValuesUtil.Y_BUTTON_POSITION,
                    GlobalValuesUtil.BUTTON_SQUARE_SIDE,
                    GlobalValuesUtil.BUTTON_SQUARE_SIDE,
                    i));
        }
    }

    public void draw(Graphics g){
        // background
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, width, height);
        // buttons
        this.drawButtons(g);

    }

    private void drawButtons(Graphics g){
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
            g.drawImage(this.selectedTile.getSprite(),
                    GlobalValuesUtil.X_BUTTON_POSITION + GlobalValuesUtil.BUTTON_X_OFFSET * 11 + GlobalValuesUtil.BUTTON_SQUARE_SIDE / 2,
                    GlobalValuesUtil.Y_BUTTON_POSITION,
                    GlobalValuesUtil.BUTTON_SQUARE_SIDE,
                    GlobalValuesUtil.BUTTON_SQUARE_SIDE,
                    null);
            g.setColor(Color.BLACK);
            g.drawRect(GlobalValuesUtil.X_BUTTON_POSITION + GlobalValuesUtil.BUTTON_X_OFFSET * 11 + GlobalValuesUtil.BUTTON_SQUARE_SIDE / 2,
                    GlobalValuesUtil.Y_BUTTON_POSITION,
                    GlobalValuesUtil.BUTTON_SQUARE_SIDE,
                    GlobalValuesUtil.BUTTON_SQUARE_SIDE);
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
        return editing.getGame().getTileManager().getSprite((id));
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
                    selectedTile = editing.getGame().getTileManager().getTile(b.getId());
                    editing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    private void saveLevel() {
        editing.saveLevel();
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
