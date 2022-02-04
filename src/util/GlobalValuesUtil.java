package util;

public class GlobalValuesUtil {
    // screen
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 640;

    // bars position
    public static final int X_BAR_POSITION = 0;
    public static final int Y_BAR_POSITION = 640;
    public static final int BAR_WIDTH = 640;
    public static final int BAR_HEIGHT = 100;

    // bottom bar buttons attributes
    public static final int BUTTON_SQUARE_SIDE = 55;
    public static final int X_BUTTON_POSITION = 10;
    public static final int Y_BUTTON_POSITION = 640 + (100 - BUTTON_SQUARE_SIDE) / 2;
    public static final int BUTTON_X_OFFSET = (int) (BUTTON_SQUARE_SIDE * 1.1f);

    // selected tile initial position
    public static final int INITIAL_X_TILE_POSITION = 640 - (BUTTON_SQUARE_SIDE + X_BUTTON_POSITION);

    // animations
    public static final int TILE_ANIMATION_SPEED = 20;

    // enemies
    public static final int ENEMY_IMAGES_AMOUNT = 4;
    public static final float ENEMY_SPEED = 2f;
}
