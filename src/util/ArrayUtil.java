package util;

import objects.PathPoint;

import java.util.ArrayList;

import static util.ConstantsUtil.Direction.*;
import static util.ConstantsUtil.Tiles.*;

public class ArrayUtil {

    /**
     *The main loop in the method is figuring out all the tiles from the start to the end. When we have the last
     * direction with the current tile and the previous tile, we can place that into the roadDirArray [local variable].
     * This way we have the array (y, x) coordinate storing the direction to the next road.
     *
     * In the and we set the array's end point with the last direction to guarantee that the mob will move until the
     * end of the map and reduce the player's lives.
     *
     * @param lvlTypeArr: created before call the method.
     * @param start
     * @param end
     * @return road's direction array.
     */
    public static int[][] getRoadDirArray(int[][] lvlTypeArr, PathPoint start, PathPoint end) {
        int[][] roadDirArray = new int[lvlTypeArr.length][lvlTypeArr[0].length];

        PathPoint currTile = start;
        int lastDir = -1;

        while (!isCurrSameAsEnd(currTile, end)) {
            PathPoint prevTile = currTile;
            currTile = getNextRoadTile(prevTile, lastDir, lvlTypeArr);
            lastDir = getDirFromPrevToCur(prevTile, currTile);

            roadDirArray[prevTile.getYCord()][prevTile.getXCord()] = lastDir;
        }

        roadDirArray[end.getYCord()][end.getXCord()] = lastDir;

        return roadDirArray;
    }

    /**
     * Figure out the direction from previous tile to the current tile.
     *
     * @param prevTile
     * @param currTile
     * @return
     */
    private static int getDirFromPrevToCur(PathPoint prevTile, PathPoint currTile) {
        // up or down
        if(prevTile.getXCord() == currTile.getXCord()){
            if(prevTile.getYCord() > currTile.getYCord())
                return UP;
            else
                return DOWN;
        // left or right
        }else{
            if(prevTile.getXCord() > currTile.getXCord())
                return LEFT;
            else
                return RIGHT;
        }
    }

    /**
     * Testing diferent directions until find the next tile in the sequence and return that tile.
     *
     * @param prevTile
     * @param lastDir
     * @param lvlTypeArr
     * @return
     */
    private static PathPoint getNextRoadTile(PathPoint prevTile, int lastDir, int[][] lvlTypeArr) {
        int testDir = lastDir;
        PathPoint testTile = getTileInDir(prevTile, testDir, lastDir);

        while (!isTileRoad(testTile, lvlTypeArr)) {
            testDir++;
            testDir %= 4;
            testTile = getTileInDir(prevTile, testDir, lastDir);
        }

        return testTile;
    }

    private static boolean isTileRoad(PathPoint testTile, int[][] lvlTypeArr) {
        return  testTile != null &&
                testTile.getYCord() >= 0 &&
                testTile.getYCord() < lvlTypeArr.length &&
                testTile.getXCord() >= 0 &&
                testTile.getXCord() < lvlTypeArr[0].length &&
                lvlTypeArr[testTile.getYCord()][testTile.getXCord()] == ROAD_TILE;
    }

    private static PathPoint getTileInDir(PathPoint prevTile, int testDir, int lastDir) {
        switch (testDir) {
            case LEFT -> {
                if (lastDir != RIGHT) return new PathPoint(prevTile.getXCord() - 1, prevTile.getYCord());
            }
            case UP -> {
                if (lastDir != DOWN) return new PathPoint(prevTile.getXCord(), prevTile.getYCord() - 1);
            }
            case RIGHT -> {
                if (lastDir != LEFT) return new PathPoint(prevTile.getXCord() + 1, prevTile.getYCord());
            }
            case DOWN -> {
                if (lastDir != UP) return new PathPoint(prevTile.getXCord(), prevTile.getYCord() + 1);
            }
        }

        return null;
    }

    private static boolean isCurrSameAsEnd(PathPoint currTile, PathPoint end) {
        return (currTile.getXCord() == end.getXCord() && currTile.getYCord() == end.getYCord());
    }

    public static int[][] transformFromArrayListTo2Dint(ArrayList<Integer> list, int ySize, int xSize) {
        int[][] newArr = new int[ySize][xSize];

        for (int y = 0; y < newArr.length; y++) {
            for (int x = 0; x < newArr[y].length; x++) {
                int index = y * ySize + x;
                newArr[y][x] = list.get(index);
            }
        }

        return newArr;
    }

    public static int[] transformFrom2DintTo1DIntArr(int[][] twoArry) {
        int[] oneArr = new int[twoArry.length * twoArry[0].length];

        for (int y = 0; y < twoArry.length; y++) {
            for (int x = 0; x < twoArry[y].length; x++) {
                int index = y * twoArry.length + x;
                oneArr[index] = twoArry[y][x];
            }
        }

        return oneArr;
    }
}
