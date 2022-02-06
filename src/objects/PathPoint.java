package objects;

public class PathPoint {
    private int xCord, yCord;
    public PathPoint(int xCord, int yCord){
        this.xCord = xCord;
        this.yCord = yCord;
    }

    // getters & setters
    public int getXCord() { return xCord; }
    public void setXCord(int xCord) { this.xCord = xCord; }
    public int getYCord() { return yCord; }
    public void setYCord(int yCord) { this.yCord = yCord; }
}
