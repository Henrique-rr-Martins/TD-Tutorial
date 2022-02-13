package enemies;

import managers.EnemyManager;

import static util.ConstantsUtil.Enemies.BAT;

public class Bat extends Enemy {
    public Bat(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, BAT, enemyManager);
    }
}
