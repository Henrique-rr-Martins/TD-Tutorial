package enemies;

import managers.EnemyManager;
import util.ConstantsUtil;
import util.GlobalValuesUtil;

import java.awt.*;
import static util.ConstantsUtil.Direction.*;
import static util.ConstantsUtil.Enemies.*;

public abstract class Enemy {

	protected EnemyManager enemyManager;
	private float x, y;
	private Rectangle bounds;
	private int health;
	private int maxHealth;
	private int id;
	private int enemyType;
	private int lastDir;
	private boolean alive = true;
	protected int slowTickLimit = 120;
	protected int slowTick = slowTickLimit;

	public Enemy(float x, float y, int id, int enemyType, EnemyManager enemyManager) {
		this.enemyManager = enemyManager;
		this.x = x;
		this.y = y;
		this.id = id;
		this.enemyType = enemyType;
		this.bounds = new Rectangle((int) x, (int) y, GlobalValuesUtil.SPRITE_SIZE, GlobalValuesUtil.SPRITE_SIZE);
		this.lastDir = -1;
		this.setStartHealth();
	}

	protected void setStartHealth() {
		this.health = getStartHealth(enemyType);
		this.maxHealth = health;
	}

	public void slow() {
		slowTick = 0;
	}

	public void move(float speed, int dir) {
		this.lastDir = dir;

		if (this.slowTick < this.slowTickLimit) {
			this.slowTick++;
			speed *= 0.5f;
		}

		switch (dir) {
		case LEFT:
			this.x -= speed;
			break;
		case UP:
			this.y -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case DOWN:
			this.y += speed;
		}

		this.updateHitBox();
	}

	protected void updateHitBox() {
		bounds.x = (int) x;
		bounds.y = (int) y;
	}

	public void setPos(int x, int y) {

		// dont use this one for move, this is for position fix
		this.x = x;
		this.y = y;
	}

	public float getHealthBarPercent() {
		return health / (float) maxHealth;
	}

	public void hurt(int dmg) {
		this.health -= dmg;
		if (health <= 0) {
			this.alive = false;
			this.enemyManager.rewardPlayer(getReward(this.enemyType));
		}
	}

	/**
	 * To kill the enemy when it reaches the end.
	 */
	public void kill() {
		this.alive = false;
		this.health = 0;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getHealth() {
		return health;
	}

	public int getId() {
		return id;
	}

	public int getEnemyType() {
		return enemyType;
	}

	public int getLastDir() {
		return this.lastDir;
	}

	public boolean isAlive() {
		return this.alive;
	}

	public boolean isSlowed() {
		return this.slowTick < this.slowTickLimit;
	}

	public void setLastDir(int newDir) {
		this.lastDir = newDir;
	};
}
