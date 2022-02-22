package managers;

import enemies.*;
import objects.PathPoint;
import scenes.Playing;
import util.ArrayUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import static util.ConstantsUtil.Direction.*;
import static util.ConstantsUtil.Enemies.*;
import static util.GlobalValuesUtil.*;

public class EnemyManager {
	private final Playing playing;
	private final BufferedImage[] enemyImgs;
	private final ArrayList<Enemy> enemies = new ArrayList<>();
	private PathPoint start, end;
	private final int hpBarWidth = SPRITE_SIZE;
	private BufferedImage slowEffectImage;
	private int[][] roadDirArray;

	private final int tileSize = DEFAULT_MAP_TILE_SIZE;
	private final int spriteSize = SPRITE_SIZE;

	public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
		this.playing = playing;
		this.enemyImgs = new BufferedImage[ENEMY_IMAGES_AMOUNT];
		this.start = start;
		this.end = end;
		this.loadEffectImg();
		this.loadEnemyImgs();
		this.loadRoadDirArray();

//        this.tempMethod();
	}

	private void loadRoadDirArray() {
		this.roadDirArray = ArrayUtil.getRoadDirArray(this.playing.getGame().getTileManager().getTypeArray(), start,
				end);
	}

	private void tempMethod() {
		for (int y = 0; y < this.roadDirArray.length; y++)
			System.out.println(Arrays.toString(this.roadDirArray[y]));
	}

	private void loadEffectImg() {
		this.slowEffectImage = LoadSave.getSpriteAtlas().getSubimage(9 * SPRITE_SIZE, 2 * SPRITE_SIZE, SPRITE_SIZE,
				SPRITE_SIZE);
	}

	private void loadEnemyImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();

		for (int i = 0; i < ENEMY_IMAGES_AMOUNT; i++)
			enemyImgs[i] = atlas.getSubimage(i * spriteSize, spriteSize, spriteSize, spriteSize);
	}

	public void update() {

		for (Enemy e : enemies)
			if (e.isAlive()) {
//                this.updateEnemyMove(e, getSpeed(e.getEnemyType()));
				this.updateEnemyMoveNew(e);
			}
	}

	private void updateEnemyMoveNew(Enemy e) {
		PathPoint currTile = this.getEnemyTile(e);
		int dir = this.roadDirArray[currTile.getYCord()][currTile.getXCord()];

		e.move(getSpeed(e.getEnemyType()), dir);

		PathPoint newTile = this.getEnemyTile(e);

		// reached the end
		if (this.isTilesTheSame(newTile, end)) {
			e.kill();
			this.playing.removeLife();
		}

		if (!this.isTilesTheSame(currTile, newTile)) {
			int newDir = this.roadDirArray[newTile.getYCord()][newTile.getXCord()];

			if (newDir != dir) {
				e.setPos(newTile.getXCord() * DEFAULT_MAP_TILE_SIZE, newTile.getYCord() * DEFAULT_MAP_TILE_SIZE);
				e.setLastDir(newDir);
			}
		}
	}

	/**
	 * Obs.: you check the (x, y) position which is top left. To avoid problems with
	 * that in code you need to add 32 to check the right position of others
	 * directions.
	 *
	 * @param e
	 * @return
	 */
	private PathPoint getEnemyTile(Enemy e) {
		switch (e.getLastDir()) {
		case LEFT:
			return new PathPoint((int) ((e.getX() + DEFAULT_MAP_TILE_SIZE - 1) / DEFAULT_MAP_TILE_SIZE),
					(int) (e.getY() / DEFAULT_MAP_TILE_SIZE));
		case UP:
			return new PathPoint((int) (e.getX() / DEFAULT_MAP_TILE_SIZE),
					(int) ((e.getY() + DEFAULT_MAP_TILE_SIZE - 1) / DEFAULT_MAP_TILE_SIZE));
		default:
			return new PathPoint((int) (e.getX() / DEFAULT_MAP_TILE_SIZE), (int) (e.getY() / DEFAULT_MAP_TILE_SIZE));
		}
	}

	private boolean isTilesTheSame(PathPoint currTile, PathPoint newTile) {
		return currTile.getYCord() == newTile.getYCord() && currTile.getXCord() == newTile.getXCord();
	}

	public void spawnEnemy(int nextEnemy) {
		this.addEnemy(nextEnemy);
	}

	private void addEnemy(int enemyType) {
		int x = this.playing.getStartPathPoint().getXCord();
		int y = this.playing.getStartPathPoint().getYCord();
		switch (enemyType) {
		case ORC:
			enemies.add(new Orc(x * tileSize, y * tileSize, ORC, this));
			break;
		case BAT:
			enemies.add(new Bat(x * tileSize, y * tileSize, BAT, this));
			break;
		case KNIGHT:
			enemies.add(new Knight(x * tileSize, y * tileSize, KNIGHT, this));
			break;
		case WOLF:
			enemies.add(new Wolf(x * tileSize, y * tileSize, WOLF, this));
		}
	}

	public void draw(Graphics g) {
		for (Enemy e : enemies) {
			if (e.isAlive()) {
				this.drawEnemy(e, g);
				this.drawHealthBar(e, g);
				this.drawEffects(e, g);
			}
		}
	}

	private void drawEffects(Enemy e, Graphics g) {
		if (e.isSlowed()) {
			g.drawImage(this.slowEffectImage, (int) e.getX(), (int) e.getY(), null);
		}
	}

	private void drawHealthBar(Enemy e, Graphics g) {
		int barHeight = 3;
		this.defineHealthBarColor(e, g);
		g.fillRect((int) e.getX() + (hpBarWidth - this.getNewBarWidth(e)) / 2, (int) e.getY() - barHeight,
				this.getNewBarWidth(e), barHeight);
	}

	private void defineHealthBarColor(Enemy e, Graphics g) {
		if (e.getHealthBarPercent() >= 0.5f)
			g.setColor(Color.GREEN);
		else if (e.getHealthBarPercent() >= 0.3f)
			g.setColor(Color.YELLOW);
		else
			g.setColor(Color.RED);
	}

	private int getNewBarWidth(Enemy e) {
		return (int) (this.hpBarWidth * e.getHealthBarPercent());
	}

	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public int getAmountOfAliveEnemies() {
		int size = 0;

		for (Enemy e : this.enemies)
			if (e.isAlive())
				size++;

		return size;
	}

	public void rewardPlayer(int reward) {
		this.playing.rewardPlayer(reward);
	}
}
