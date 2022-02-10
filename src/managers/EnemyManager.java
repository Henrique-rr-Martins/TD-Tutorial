package managers;

import enemies.*;
import scenes.Playing;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static util.ConstantsUtil.Direction.*;
import static util.ConstantsUtil.Tiles.ROAD_TILE;
import static util.ConstantsUtil.Enemies.*;
import static util.GlobalValuesUtil.*;

public class EnemyManager {
    private final Playing playing;
    private final BufferedImage[] enemyImgs;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final int barHeight = 3;
    private final int hpBarWidth = SPRITE_SIZE;

    private final int tileSize = DEFAULT_MAP_TILE_SIZE;
    private final int spriteSize = SPRITE_SIZE;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        this.enemyImgs = new BufferedImage[ENEMY_IMAGES_AMOUNT];
        this.loadEnemyImgs();

        this.addEnemy(ORC);
        this.addEnemy(BAT);
        this.addEnemy(KNIGHT);
        this.addEnemy(WOLF);
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for (int i = 0; i < ENEMY_IMAGES_AMOUNT; i++)
            enemyImgs[i] = atlas.getSubimage(i * spriteSize, spriteSize, spriteSize, spriteSize);
    }

    public void update() {
        ArrayList<Enemy> deadEnemies = new ArrayList<>();

        for (Enemy e : enemies)
            if (e.isAlive())
                this.updateEnemyMove(e, getSpeed(e.getEnemyType()));
            else
                deadEnemies.add(e);

        for (Enemy e : deadEnemies) {
            this.enemies.remove(e);
        }
    }

    private boolean isAtEnd(Enemy e) {
        return e.getX() == this.playing.getEndPathPoint().getXCord() * tileSize &&
                e.getY() == this.playing.getEndPathPoint().getYCord() * tileSize;
    }

    private void updateEnemyMove(Enemy e, float speed) {
        int lastDir = e.getLastDir();

        if (lastDir == -1)
            this.setNewDirectionAndMove(e, speed);

        int newX = (int) (e.getX() + this.getSpeedAndWidth(speed, lastDir));
        int newY = (int) (e.getY() + this.getSpeedAndHeight(speed, lastDir));

        if (this.getTileType(newX, newY) == ROAD_TILE) {
            // keep moving in same direction
            e.move(speed, e.getLastDir());
        } else if (this.isAtEnd(e)) {
            // reached the end
            System.out.println("Lives lost!");
        } else {
            // find new direction
            this.setNewDirectionAndMove(e, speed);
        }
    }

    private void setNewDirectionAndMove(Enemy e, float speed) {
        int dir = e.getLastDir();

        // move into the current until 100%;
        int xCord = (int) (e.getX() / spriteSize);
        int yCord = (int) (e.getY() / spriteSize);

        this.fixEnemyOffSetTile(e, dir, xCord, yCord);

        if (isAtEnd(e))
            return;

        if (dir == LEFT || dir == RIGHT) {
            int newY = (int) (e.getY() + this.getSpeedAndHeight(speed, UP));
            if (this.getTileType((int) e.getX(), newY) == ROAD_TILE)
                e.move(speed, UP);
            else
                e.move(speed, DOWN);
        } else {
            int newX = (int) (e.getX() + this.getSpeedAndWidth(speed, RIGHT));
            if (this.getTileType(newX, (int) e.getY()) == ROAD_TILE)
                e.move(speed, RIGHT);
            else
                e.move(speed, LEFT);
        }
    }

    private void fixEnemyOffSetTile(Enemy e, int dir, int xCord, int yCord) {
        switch (dir) {
            case RIGHT:
                // there are 20 tiles, but as array we need to subtract 1 to work with max_tile = 19
                if (xCord < SCREEN_WIDTH / spriteSize - 1)
                    xCord++;
                break;
            case DOWN:
                // there are 20 tiles, but as array we need to subtract 1 to work with max_tile = 19
                if (yCord < SCREEN_HEIGHT / spriteSize - 1)
                    yCord++;
                break;
        }

        e.setPos(xCord * spriteSize, yCord * spriteSize);
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x, y);
    }

    private float getSpeedAndHeight(float speed, int dir) {
        if (dir == UP)
            return -speed;
        else if (dir == DOWN)
            return speed + spriteSize;

        return 0;
    }

    private float getSpeedAndWidth(float speed, int dir) {
        if (dir == LEFT)
            return -speed;
        else if (dir == RIGHT)
            return speed + spriteSize;

        return 0;
    }

    public void addEnemy(int enemyType) {
        int x = this.playing.getStartPathPoint().getXCord();
        int y = this.playing.getStartPathPoint().getYCord();
        switch (enemyType) {
            case ORC -> enemies.add(new Orc(x * tileSize, y * tileSize, ORC));
            case BAT -> enemies.add(new Bat(x * tileSize, y * tileSize, BAT));
            case KNIGHT -> enemies.add(new Knight(x * tileSize, y * tileSize, KNIGHT));
            case WOLF -> enemies.add(new Wolf(x * tileSize, y * tileSize, WOLF));
        }
    }

    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                this.drawEnemy(e, g);
                this.drawHealthBar(e, g);
            }
        }
    }

    private void drawHealthBar(Enemy e, Graphics g) {
        this.defineHealthBarColor(e, g);
        g.fillRect((int) e.getX() + (hpBarWidth - this.getNewBarWidth(e)) / 2,
                (int) e.getY() - barHeight,
                this.getNewBarWidth(e),
                barHeight);
    }

    private int getNewBarWidth(Enemy e) {
        return (int) (this.hpBarWidth * e.getHealthBarPercent());
    }

    private void defineHealthBarColor(Enemy e, Graphics g) {
        if (e.getHealthBarPercent() >= 0.5f)
            g.setColor(Color.GREEN);
        else if (e.getHealthBarPercent() >= 0.3f)
            g.setColor(Color.YELLOW);
        else
            g.setColor(Color.RED);
    }

    private void drawEnemy(Enemy e, Graphics g) {
        g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
