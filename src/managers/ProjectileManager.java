package managers;

import enemies.Enemy;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;
import util.ConstantsUtil;
import util.LoadSave;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;
import static util.ConstantsUtil.Projectiles.*;
import static util.ConstantsUtil.Towers.*;
import static util.GlobalValuesUtil.*;

public class ProjectileManager {

	private static List<Integer> projectilesToRotate;

	static {
		projectilesToRotate = List.of(ARROW);
	}

	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private ArrayList<Explosion> explosionsToDestroy = new ArrayList<>();
	private BufferedImage[] projImages, explosionImgs;
	private int projectileId = 0;

	public ProjectileManager(Playing playing) {
		this.playing = playing;
		this.projImages = new BufferedImage[PROJECTILE_AMOUNT];
		this.importImages();
	}

	private void importImages() {
		// x 7 8 9 y 1
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for (int i = 0; i < 3; i++)
			projImages[i] = atlas.getSubimage((7 + i) * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);

		this.importExplosionImgs(atlas);
	}

	private void importExplosionImgs(BufferedImage atlas) {
		this.explosionImgs = new BufferedImage[EXPLOSION_IMGS_AMOUNT];

		for (int i = 0; i < explosionImgs.length; i++) {
			this.explosionImgs[i] = atlas.getSubimage(i * SPRITE_SIZE, 2 * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
		}
	}

	public void newProjectile(Tower t, Enemy e) {
		int projectileType = this.getProjectileType(t);

		int xDist = (int) (t.getX() - e.getX());
		int yDist = (int) (t.getY() - e.getY());
		int totalDist = abs(xDist) + abs(yDist);

		float xPercent = (float) abs(xDist) / totalDist;

		float xSpeed = xPercent * getSpeed(projectileType);
		float ySpeed = getSpeed(projectileType) - xSpeed;

		if (t.getX() > e.getX())
			xSpeed *= -1;
		if (t.getY() > e.getY())
			ySpeed *= -1;

		float rotate = 0;

		if (this.checkIfIsProjectileToRotate(projectileType)) {
			float arcValue = (float) atan((float) yDist / xDist);
			rotate = (float) toDegrees(arcValue);

			if (xDist < 0)
				rotate += 180;
		}

		for (Projectile p : this.projectiles)
			if (!p.isActive() && p.getProjectileType() == projectileType) {
				p.reuse(t.getX() + (float) SPRITE_SIZE / 2, t.getY() + (float) SPRITE_SIZE / 2, xSpeed, ySpeed,
						t.getDmg(), rotate);
				return;
			}

		projectiles.add(new Projectile(t.getX() + (float) SPRITE_SIZE / 2, t.getY() + (float) SPRITE_SIZE / 2, xSpeed,
				ySpeed, t.getDmg(), rotate, projectileId++, projectileType));
	}

	public void update() {
		for (Projectile p : this.projectiles)
			if (p.isActive()) {
				p.move();
				if (this.isProjectileHittingEnemy(p)) {
					p.setActive(false);
					if (p.getProjectileType() == BOMB) {
						this.explosions.add(new Explosion(p.getPos()));
						this.explodeOnEnemies(p);
					}
				} else if (this.isProjectileOutsideBounds(p)) {
					p.setActive(false);
				}
			}

		for (Explosion e : this.explosions) {
			if (e.explosionIndex < this.explosionImgs.length)
				e.update();
			else
				this.explosionsToDestroy.add(e);
		}

		for (Explosion e : this.explosionsToDestroy)
			this.explosions.remove(e);

		this.explosionsToDestroy.clear();
	}

	private void explodeOnEnemies(Projectile p) {
		for (Enemy e : playing.getEnemyManager().getEnemies()) {
			if (e.isAlive()) {
				float xDist = abs(p.getPos().x - e.getX());
				float yDist = abs(p.getPos().y - e.getY());

				float realDist = (float) hypot(xDist, yDist);

				if (realDist <= EXPLOSION_RADIUS)
					e.hurt(p.getDmg());
			}
		}
	}

	private boolean isProjectileHittingEnemy(Projectile p) {
		for (Enemy e : playing.getEnemyManager().getEnemies()) {
			if (e.isAlive() && e.getBounds().contains(p.getPos())) {
				e.hurt(p.getDmg());
				if (p.getProjectileType() == CHAINS)
					e.slow();

				return true;
			}
		}

		return false;
	}

	private boolean isProjectileOutsideBounds(Projectile p) {
		return !(p.getPos().x >= 0 && p.getPos().x <= 800 && p.getPos().y >= 0 && p.getPos().y <= 800);
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		for (Projectile p : projectiles)
			if (p.isActive()) {
				if (this.checkIfIsProjectileToRotate(p.getProjectileType())) {
					g2d.translate(p.getPos().x, p.getPos().y);
					g2d.rotate(toRadians(p.getRotation()));
					g2d.drawImage(this.projImages[p.getProjectileType()], -SPRITE_SIZE / 2, -SPRITE_SIZE / 2, null);
					g2d.rotate(-toRadians(p.getRotation()));
					g2d.translate(-p.getPos().x, -p.getPos().y);
				} else {
					g2d.drawImage(this.projImages[p.getProjectileType()], (int) p.getPos().x - SPRITE_SIZE / 2,
							(int) p.getPos().y - SPRITE_SIZE / 2, null);
				}
			}

		this.drawExplosion(g2d);
	}

	private void drawExplosion(Graphics2D g2d) {
		for (Explosion e : this.explosions) {
			if (e.explosionIndex < this.explosionImgs.length)
				g2d.drawImage(this.explosionImgs[e.explosionIndex], (int) e.pos.x - SPRITE_SIZE / 2,
						(int) e.pos.y - SPRITE_SIZE / 2, null);
		}
	}

	private int getProjectileType(Tower t) {
		switch (t.getTowerType()) {
		case ARCHER:
			return ARROW;
		case CANNON:
			return BOMB;
		case WIZARD:
			return CHAINS;
		default:
			return -1;
		}
	}

	private boolean checkIfIsProjectileToRotate(int projectileType) {
		return projectilesToRotate.contains(projectileType);
	}

	private class Explosion {

		private Point2D.Float pos;
		private int explosionTick = 0, explosionIndex = 0;

		public Explosion(Point2D.Float pos) {
			this.pos = pos;
		}

		private void update() {
			this.explosionTick++;
			if (this.explosionTick >= 12) {
				this.explosionTick = 0;
				this.explosionIndex++;
			}

		}
	}
}
