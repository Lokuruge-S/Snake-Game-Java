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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false;
        }
        Point point = (Point) o;
        if (this.getX() == point.getX() && this.getY() == point.getY()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 19 * this.getX() + this.getY();
    }

    @Override
    public String toString() {
        return ("(" + xcoord + ", " + ycoord + ")");
    }
}