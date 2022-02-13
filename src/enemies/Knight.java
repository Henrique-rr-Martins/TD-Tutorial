package enemies;

import managers.EnemyManager;

import static util.ConstantsUtil.Enemies.KNIGHT;

public class Knight extends Enemy {
    public Knight(float x, float y, int id, EnemyManager enemyManager) {
        super(x, y, id, KNIGHT, enemyManager);
    }
}
