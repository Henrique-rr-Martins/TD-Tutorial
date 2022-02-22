package util;

public class ConstantsUtil {

    public static class Projectiles{
        public static final int ARROW = 0;
        public static final int CHAINS = 1;
        public static final int BOMB = 2;

        public static float getSpeed(int projectileType){
             switch(projectileType){
                case ARROW: return 8f;
                case BOMB: return 4f;
                case CHAINS: return 6f;
                default: return 0f;
            }
        }
    }

    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
        public static final int START_TILE = 3;
        public static final int END_TILE = 4;
    }

    public static class Enemies {
        public static final int ORC = 0;
        public static final int BAT = 1;
        public static final int KNIGHT = 2;
        public static final int WOLF = 3;

        public static int getReward(int enemyType){
             switch (enemyType) {
                case ORC: return 5;
                case BAT: return 5;
                case KNIGHT: return 25;
                case WOLF: return 10;
                default: return 0;
            }
        }

        public static float getSpeed(int enemyType) {
             switch (enemyType) {
                case BAT: return 0.65f;
                case KNIGHT: return 0.3f;
                case WOLF: return 0.75f;
                default: return 0.5f;
            }
        }

        public static int getStartHealth(int enemyType) {
             switch (enemyType){
                case BAT: return 60;
                case KNIGHT: return 250;
                case WOLF: return 85;
                default: return 100;
            }
        }
    }

    public static class Towers {
        public static final int CANNON = 0;
        public static final int ARCHER = 1;
        public static final int WIZARD = 2;

        public static String getName(int towerType) {
             switch (towerType) {
                case CANNON: return "CANNON";
                case ARCHER: return "ARCHER";
                case WIZARD: return "WIZARD";
                default: return "";
            }
        }

        public static int getCost(int towerType){
             switch (towerType){
                case CANNON: return 65;
                case ARCHER: return 30;
                case WIZARD: return 45;
                default: return 0;
            }
        }

        public static float getDefaultCooldown(int towerType){
             switch(towerType){
                case CANNON: return 120;
                case ARCHER: return 25;
                case WIZARD: return 40;
                default: return 1f;
            }
        }

        public static float getDefaultRange(int towerType){
             switch(towerType){
                case CANNON: return 100;
                case ARCHER: return 100;
                case WIZARD: return 100;
                default: return 1f;
            }
        }

        public static int getStartDamage(int towerType){
             switch(towerType){
                case CANNON: return 15;
                case ARCHER: return 5;
                case WIZARD: return 0;
                default: return 1;
            }
        }
    }
}
