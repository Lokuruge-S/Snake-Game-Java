public class Point {
    private int xcoord;
    private int ycoord;

    public Point(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    public int getX() {
        return this.xcoord;
    }

    public int getY() {
        return this.ycoord;
    }

    public void setX(int xcoord) {
        this.xcoord = xcoord;
    }

    public void setY(int ycoord) {
        this.ycoord = ycoord;
    }
}