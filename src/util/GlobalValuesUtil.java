package util;

public class GlobalValuesUtil {

    // map size
    public static final int DEFAULT_MAP_TILE_SIZE = 32;

    // play screen
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 640;

    // panel screen
    public static final int PANEL_WIDTH = 640;
    public static final int PANEL_HEIGHT = 800;

    // sprites
    public static final int SPRITE_SIZE = 32;

    // bars position
    public static final int X_BAR_POSITION = 0;
    public static final int Y_BAR_POSITION = 640;
    public static final int BAR_WIDTH = 640;
    public static final int BAR_HEIGHT = PANEL_HEIGHT - SCREEN_HEIGHT;

    // bottom bar buttons attributes
    public static final int BUTTON_SQUARE_SIDE = 55;
    public static final int X_BUTTON_POSITION = 10;
    public static final int Y_BUTTON_POSITION = 640 + (100 - BUTTON_SQUARE_SIDE) / 2;
    public static final int BUTTON_X_OFFSET = (int) (BUTTON_SQUARE_SIDE * 1.1f);
    public static final int BUTTON_Y_OFFSET = (int) (BUTTON_SQUARE_SIDE * 1.1f);

    // selected tile initial position
    public static final int INITIAL_X_TILE_POSITION = 640 - (BUTTON_SQUARE_SIDE + X_BUTTON_POSITION);

    // data tower display
    public static final int DATA_TOWER_DISPLAY_SIZE = 30;
    public static final int X_TOWER_TILE_POSITION = (int) (640 - (BUTTON_SQUARE_SIDE + X_BUTTON_POSITION) - DATA_TOWER_DISPLAY_SIZE * 2.5f) - 30;
    public static final int Y_TOWER_TILE_POSITION = 640 + (100 - BUTTON_SQUARE_SIDE) / 2 - 15;
    public static final int X_TOWER_NAME_POSITION = X_TOWER_TILE_POSITION  + DATA_TOWER_DISPLAY_SIZE + 10;
    public static final int Y_TOWER_NAME_POSITION = Y_TOWER_TILE_POSITION + 20;
    public static final int Y_TOWER_ID_POSITION = Y_TOWER_NAME_POSITION + 10;

    // animations
    public static final int TILE_ANIMATION_SPEED = 20;

    // enemies
    public static final int ENEMY_IMAGES_AMOUNT = 4;

    // towers
    public static final int TOWER_AMOUNT = 3;

    // projectiles
    public static final int PROJECTILE_AMOUNT = 3;
    public static final int EXPLOSION_IMGS_AMOUNT = 7;
    public static final float EXPLOSION_RADIUS = 40;
}
