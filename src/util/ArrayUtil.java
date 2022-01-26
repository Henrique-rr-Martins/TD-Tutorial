package util;

import java.util.ArrayList;

public class ArrayUtil {

    public static int[][] transformFromArrayListTo2Dint(ArrayList<Integer> list, int ySize, int xSize){
        int[][] newArr = new int[ySize][xSize];

        for(int y = 0; y < newArr.length; y++){
            for(int x = 0; x < newArr[y].length; x++){
                int index = y*ySize + x;
                newArr[y][x] = list.get(index);
            }
        }

        return newArr;
    }

    public static int[] transformFrom2DintTo1DIntArr(int[][] twoArry){
        int [] oneArr = new int[twoArry.length * twoArry[0].length];

        for(int y = 0; y < twoArry.length; y++){
            for(int x = 0; x < twoArry[y].length; x++){
                int index = y * twoArry.length + x;
                oneArr[index] = twoArry[y][x];
            }
        }

        return oneArr;
    }
}
