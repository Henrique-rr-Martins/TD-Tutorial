package ui;

import java.awt.*;

public class MyButton {

    private String text;
    public int x, y, width, height, id;

    private Rectangle bounds;
    private boolean mouseOver, mousePressed;

    /**
     * for normal buttons
     */
    public MyButton(String text, int x, int y, int width, int height){
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = -1;

        this.initBounds();
    }

    /**
     * for tile buttons
     */
    public MyButton(String text, int x, int y, int width, int height, int id){
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;

        this.initBounds();
    }

    private void initBounds(){
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g){
        // body
        this.drawBody(g);
        // border
        this.drawBorder(g);
        // text
        this.drawText(g);
    }

    private void drawBorder(Graphics g) {
        if(this.mousePressed){
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width - 1, height);
            g.drawRect(x + 1, y + 1, width - 3, height - 1);
            g.drawRect(x + 2, y + 1, width - 4, height - 2);
            g.drawRect(x + 2, y + 1, width - 5, height - 3);
        }else{
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);

        }
    }

    private void drawBody(Graphics g) {
        if(this.mouseOver){
            g.setColor(Color.GRAY);
        }else{
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, width, height);
    }

    private void drawText(Graphics g) {
        int fontWidth = g.getFontMetrics().stringWidth(text);
        int fontHeight = g.getFontMetrics().getHeight();
        g.drawString(text, x - fontWidth / 2 + width / 2, y + fontHeight / 2 + height / 2);
    }

    public void resetBooleans(){
        this.mouseOver = false;
        this.mousePressed = false;
    }

    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }

    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }

    public boolean isMouseOver(){ return this.mouseOver; }

    public boolean isMousePressed(){ return this.mousePressed; }

    public Rectangle getBounds(){
        return bounds;
    }

    public int getId(){ return this.id; }

}
