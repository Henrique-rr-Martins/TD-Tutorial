package util;

import java.io.File;

public class StringUtil {
    private static String homePath = System.getProperty("user.home");
    private static String saveFolder = "TDTutorial";

    private static String LEVEL_NAME = "level.txt";

    public static String FOLDER_PATH = homePath + File.separator + saveFolder;
    public static String FILE_PATH = homePath + File.separator + saveFolder + File.separator + LEVEL_NAME;

    public static String TXT_GAME_OVER = "Game Over!";
    public static String TXT_FILE_DOES_NOT_EXIST = "File does not exist!";
    public static String TXT_FILE_ALREADY_EXISTS = "%s already exists!";
    public static String FPS_UPD_INFOS = "FPS: %d | UPS: %d";
    public static String SPRITE_ATLAS = "spriteatlas.png";
}
