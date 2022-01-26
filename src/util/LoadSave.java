package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
        File txtFile = new File("resource/testTextFile.txt");

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
            System.out.println(filePath.concat(" already exists!"));
            return;
        }

        try {
            newLevel.createNewFile();
            writeToFile(newLevel, idArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(File file, int[] idArr) {
        File txtFile = new File("resource/testTextFile.txt");
        try(PrintWriter pw = new PrintWriter(file)){
            for(Integer i : idArr)
                pw.println(i);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveLevel(String name, int[][] idArr){
        var filePath = String.format(StringUtil.DEFAULT_PATH, name);
        File newLevel = new File(filePath);

        if(newLevel.exists()){
            writeToFile(newLevel, ArrayUtil.transformFrom2DintTo1DIntArr(idArr));
        }else{
            System.out.println("File does not exists!");
            return;
        }
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

    public static int[][] getLevelData(String name){
        var filePath = String.format(StringUtil.DEFAULT_PATH, name);
        File lvlFile = new File(filePath);

        System.out.println(filePath);
        if(!lvlFile.exists()){
            System.out.println("File does not exists!");
            return null;
        }

        ArrayList<Integer> list = readFromFile(lvlFile);

        return ArrayUtil.transformFromArrayListTo2Dint(list, 20, 20);
    }
}
