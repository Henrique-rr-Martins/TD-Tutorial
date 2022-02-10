package ui;

import objects.Tile;
import scenes.Editing;
import util.GlobalValuesUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static main.GameStates.MENU;
import static main.GameStates.setGameState;

public class ToolBar extends Bar {

    private Editing editing;
    private MyButton bMenu, bSave;
    private MyButton  bPathStart, bPathEnd;
    private BufferedImage pathStart, pathEnd;
    private Tile selectedTile;

    private int buttonSize = GlobalValuesUtil.BUTTON_SQUARE_SIDE;
    private int spriteSize = GlobalValuesUtil.SPRITE_SIZE;
    private int xButtonPosition = GlobalValuesUtil.X_BUTTON_POSITION;
    private int yButtonPosition = GlobalValuesUtil.Y_BUTTON_POSITION;
    private int xButtonOffset = GlobalValuesUtil.BUTTON_X_OFFSET;
    private int yButtonOffset = GlobalValuesUtil.BUTTON_Y_OFFSET;
    private int initialXTilePosition = GlobalValuesUtil.INITIAL_X_TILE_POSITION;

//    private ArrayList<MyButton> tileButtons = new ArrayList<>();
    private Map<MyButton, ArrayList<Tile>> map = new HashMap<>();

    private MyButton bGrass, bWater, bRoadStraight, bRoadCorner, bWaterCorner, bBeaches, bIsland;
    private MyButton currentButton;
    private int currentIndex;

    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        this.initPathImgs();
        this.initButtons();
    }

    private void initPathImgs() {
        this.pathStart = LoadSave.getSpriteAtlas().getSubimage(spriteSize * 7, spriteSize * 2, spriteSize, spriteSize);
        this.pathEnd = LoadSave.getSpriteAtlas().getSubimage(spriteSize * 8, spriteSize * 2, spriteSize, spriteSize);
    }

    private void initButtons() {
        int i = 0;

        this.bMenu = new MyButton("Menu", xButtonPosition, yButtonPosition, buttonSize, buttonSize);
        this.bSave = new MyButton("Save", xButtonPosition + xButtonOffset, yButtonPosition, buttonSize, buttonSize);

        this.bGrass = new MyButton("Grass", xButtonPosition + xButtonOffset * 2, yButtonPosition, buttonSize, buttonSize, i++);
        this.bWater = new MyButton("Water", xButtonPosition + xButtonOffset * 3, yButtonPosition, buttonSize, buttonSize, i++);

        this.initMapButton(bRoadStraight, editing.getGame().getTileManager().getRoadsStraight(), xButtonPosition + xButtonOffset, yButtonPosition, xButtonOffset, buttonSize, buttonSize, i++);
        this.initMapButton(bRoadCorner, editing.getGame().getTileManager().getRoadsCorner(), xButtonPosition + xButtonOffset, yButtonPosition, xButtonOffset, buttonSize, buttonSize, i++);
        this.initMapButton(bWaterCorner, editing.getGame().getTileManager().getWaterCorner(), xButtonPosition + xButtonOffset, yButtonPosition, xButtonOffset, buttonSize, buttonSize, i++);
        this.initMapButton(bBeaches, editing.getGame().getTileManager().getBeaches(), xButtonPosition + xButtonOffset, yButtonPosition, xButtonOffset, buttonSize, buttonSize, i++);
        this.initMapButton(bIsland, editing.getGame().getTileManager().getIslands(), xButtonPosition + xButtonOffset, yButtonPosition, xButtonOffset, buttonSize, buttonSize, i++);

        this.bPathStart = new MyButton("PathStart", xButtonPosition + xButtonOffset * 2, yButtonPosition + yButtonOffset, buttonSize, buttonSize, i++);
        this.bPathEnd = new MyButton("PathEnd", xButtonPosition + xButtonOffset * 3, yButtonPosition + yButtonOffset, buttonSize, buttonSize, i++);
    }

    private void initMapButton(MyButton b, ArrayList<Tile> list, int x, int y, int xOff, int w, int h, int id){
        b = new MyButton("", x + xOff + xOff * id, y, w, h, id);
        map.put(b, list);
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    public void rotateSprite() {
        this.currentIndex++;
        if(this.currentButton != null){
            if(this.currentIndex >= map.get(this.currentButton).size())
                this.currentIndex = 0;
            this.selectedTile = map.get(this.currentButton).get(this.currentIndex);
            editing.setSelectedTile(selectedTile);
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

        this.drawPathButton(g, bPathStart, pathStart);
        this.drawPathButton(g, bPathEnd, pathEnd);

        // tile buttons
        this.drawNormalButton(g, bGrass);
        this.drawNormalButton(g, bWater);
        this.drawMapButtons(g);
        this.drawSelectedTile(g);
    }

    private void drawPathButton(Graphics g, MyButton b, BufferedImage img) {
        g.drawImage(img, b.x, b.y, b.width, b.height, null);
        this.drawButtonFeedback(g, b);
    }

    private void drawNormalButton(Graphics g, MyButton b) {
        g.drawImage(getButtImage(b.getId()), b.x, b.y, b.width, b.height, null);
        this.drawButtonFeedback(g, b);
    }

    private void drawMapButtons(Graphics g) {
        for(Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()){
            MyButton b = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();

            g.drawImage(img, b.x, b.y, b.width, b.height, null);
            super.drawButtonFeedback(g, b);
        }
    }

    private void drawSelectedTile(Graphics g) {
        if(selectedTile != null){
            g.drawImage(this.selectedTile.getSprite(), initialXTilePosition, yButtonPosition, buttonSize, buttonSize, null);
            g.setColor(Color.BLACK);
            g.drawRect(initialXTilePosition, yButtonPosition, buttonSize, buttonSize);
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
        } else if(bWater.getBounds().contains(x, y)){
            selectedTile = editing.getGame().getTileManager().getTile(bWater.getId());
            editing.setSelectedTile(selectedTile);
        } else if(bGrass.getBounds().contains(x, y)){
            selectedTile = editing.getGame().getTileManager().getTile(bGrass.getId());
            editing.setSelectedTile(selectedTile);
        } else if(bPathStart.getBounds().contains(x, y)){
            selectedTile = new Tile(pathStart,  -1, -1);
            editing.setSelectedTile(selectedTile);
        } else if(bPathEnd.getBounds().contains(x, y)){
            selectedTile = new Tile(pathEnd,  -2, -2);
            editing.setSelectedTile(selectedTile);
        } else {
            for(MyButton b : map.keySet()){
                if(b.getBounds().contains(x, y)){
                    selectedTile = map.get(b).get(0);
                    editing.setSelectedTile(selectedTile);
                    this.currentButton = b;
                    this.currentIndex = 0;
                    return;
                }
            }
        }
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bSave.setMouseOver(false);
        bWater.setMouseOver(false);
        bGrass.setMouseOver(false);
        bPathStart.setMouseOver(false);
        bPathEnd.setMouseOver(false);
        for(MyButton b : map.keySet())
            b.setMouseOver(false);

        if(bMenu.getBounds().contains(x, y)) {
            bMenu.setMouseOver(true);
        } else if(bSave.getBounds().contains(x, y)){
            bSave.setMouseOver(true);
        } else if(bWater.getBounds().contains(x, y)){
            bWater.setMouseOver(true);
        } else if(bGrass.getBounds().contains(x, y)){
            bGrass.setMouseOver(true);
        } else if(bPathStart.getBounds().contains(x, y)){
            bPathStart.setMouseOver(true);
        } else if(bPathEnd.getBounds().contains(x, y)){
            bPathEnd.setMouseOver(true);
        } else {
            for(MyButton b : map.keySet())
                if(b.getBounds().contains(x, y)){
                    b.setMouseOver(true);
                    return;
                }
        }
    }

    public void mousePressed(int x, int y) {
        if(bMenu.getBounds().contains(x, y)){
            bMenu.setMousePressed(true);
        } else if(bSave.getBounds().contains(x, y)){
            bSave.setMousePressed(true);
        } else if(bWater.getBounds().contains(x, y)){
            bWater.setMousePressed(true);
        } else if(bGrass.getBounds().contains(x, y)){
            bGrass.setMousePressed(true);
        } else if(bPathStart.getBounds().contains(x, y)){
            bPathStart.setMousePressed(true);
        } else if(bPathEnd.getBounds().contains(x, y)){
            bPathEnd.setMousePressed(true);
        } else {
            for(MyButton b : map.keySet()){
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
        bWater.resetBooleans();
        bGrass.resetBooleans();
        bPathStart.resetBooleans();
        bPathEnd.resetBooleans();
        for(MyButton b : map.keySet())
            b.resetBooleans();
    }

    public BufferedImage getPathStart(){ return this.pathStart; }
    public BufferedImage getPathEnd(){ return this.pathEnd; }
}
