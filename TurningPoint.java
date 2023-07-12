public class TurningPoint extends Point {

    private int direction;

    public TurningPoint (int xcoord, int ycoord, int direction) {
        super(xcoord, ycoord);
        switch (direction) {
            case 0:
            case 1:
            case 2:
            case 3:
                this.direction = direction;
                break;
            default:
                this.direction = 0;
                break;
        }
    }
    
    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        switch (direction) {
            case 0:
            case 1:
            case 2:
            case 3:
                this.direction = direction;
                break;
            default:
                this.direction = 0;
                break;
        }
    }
}
