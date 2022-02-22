package util;

import objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static util.StringUtil.*;

public class LoadSave {
    public static BufferedImage getSpriteAtlas(){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        try {
            img = ImageIO.read(is);
        }catch (Exception e){
            e.printStackTrace();
        }

        return img;
    }

    // txt file
    public static void createFile(){
        File txtFile = new File(String.format(DEFAULT_PATH, "testTextFile"));

        try{
            txtFile.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void createLevel(String name, int[] idArr){
        var filePath = String.format(StringUtil.DEFAULT_PATH, name);
        File newLevel = new File(filePath);

        if(newLevel.exists()){
            System.out.println(String.format(TXT_FILE_ALREADY_EXISTS, filePath));
            return;
        }

        try {
            newLevel.createNewFile();
            writeToFile(newLevel, idArr, new PathPoint(0, 0), new PathPoint(0, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(File file, int[] idArr, PathPoint start, PathPoint end) {
        try(PrintWriter pw = new PrintWriter(file)){
            for(Integer i : idArr)
                pw.println(i);

            pw.println(start.getXCord());
            pw.println(start.getYCord());
            pw.println(end.getXCord());
            pw.println(end.getYCord());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveLevel(String name, int[][] idArr, PathPoint start, PathPoint end){
        var filePath = String.format(StringUtil.DEFAULT_PATH, name);
        File newLevel = new File(filePath);

        if(!newLevel.exists()){
            System.out.println(TXT_FILE_DOES_NOT_EXIST);
            return;
        }

        writeToFile(newLevel, ArrayUtil.transformFrom2DintTo1DIntArr(idArr), start, end);
    }

    public static ArrayList<Integer> readFromFile(File lvlFile){
        ArrayList<Integer> list = new ArrayList<>();

        try(Scanner sc = new Scanner(lvlFile)){
            while(sc.hasNext()){
                list.add(Integer.parseInt(sc.nextLine()));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<PathPoint> getLevelPathPoints(String name){
        var filePath = String.format(StringUtil.DEFAULT_PATH, name);
        File lvlFile = new File(filePath);

        if(!lvlFile.exists()){
            System.out.println(TXT_FILE_DOES_NOT_EXIST);
            return null;
        }

        ArrayList<Integer> list = readFromFile(lvlFile);

        ArrayList<PathPoint> points = new ArrayList<>();

        points.add(new PathPoint(list.get(400), list.get(401)));
        points.add(new PathPoint(list.get(402), list.get(403)));

        return points;
    }

    public static int[][] getLevelData(String name){
        var filePath = String.format(StringUtil.DEFAULT_PATH, name);
        File lvlFile = new File(filePath);

        if(!lvlFile.exists()){
            System.out.println(TXT_FILE_DOES_NOT_EXIST);
            return null;
        }

        ArrayList<Integer> list = readFromFile(lvlFile);

        return ArrayUtil.transformFromArrayListTo2Dint(list, 20, 20);
    }
}
