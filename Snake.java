import java.util.ArrayList;

public class Snake {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    private String[][] board;
    private int direction;
    private Point head;
    private int length;
    private Point food;
    private ArrayList<Point> turnPoints = new ArrayList<Point>();
    private ArrayList<Integer> turnDirections = new ArrayList<Integer>();
    private boolean snakeMade;
    private boolean bitesBody = false;

    public Snake (String[][] board, Point head, int direction, int length) {
        this.board = board;
        this.head = head;

        switch (direction) {
            case Snake.NORTH: 
                this.direction = direction;
                break;
            
            case Snake.EAST:
                this.direction = direction;
                break;

            case Snake.SOUTH:
                this.direction = direction;
                break;

            case Snake.WEST:
                this.direction = direction;
                break;
            
            default:
                this.direction = Snake.NORTH;
        }

        this.length = length;
    }

    public void setHead (Point head) {
        this.head = head;
    }

    public Point getHead () {
        return this.head;
    }

    public void setDirection (int direction) {
        switch (direction) {
            case Snake.NORTH: 
                this.direction = direction;
                break;
            
            case Snake.EAST:
                this.direction = direction;
                break;

            case Snake.SOUTH:
                this.direction = direction;
                break;

            case Snake.WEST:
                this.direction = direction;
                break;
            
            default:
                this.direction = Snake.NORTH;
        }
    }

    public int getDirection () {
        return this.direction;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return this.length;
    }

    public void grow() {
        this.length++;
    }

    public void setFood(Point food) {
        this.food = food;
    }

    public String[][] makeSnake() {
        switch (direction) {
            case Snake.NORTH:
                board[head.getY()][head.getX()] = " ^ ";
                break;
            
            case Snake.EAST:
                board[head.getY()][head.getX()] = " > ";
                break;

            case Snake.SOUTH:
                board[head.getY()][head.getX()] = " v ";
                break;

            case Snake.WEST:
                board[head.getY()][head.getX()] = " < ";
                break;
        }

        snakeMade = false;
        bitesBody = false;
        bendSnake(head, direction, 1);

        return this.board;
    }

    public boolean shiftHead() {
        switch (direction) {
            case Snake.NORTH:
                head.setY(head.getY() - 1);
                break;
            
            case Snake.EAST:
                head.setX(head.getX() + 1);
                break;

            case Snake.SOUTH:
                head.setY(head.getY() + 1);
                break;

            case Snake.WEST:
                head.setX(head.getX() - 1);
                break;
        }

        if (head.equals(food)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addTurnPoint() {
        turnPoints.add(new Point(head.getX(), head.getY()));
        turnDirections.add(this.direction);
    }

    private void bendSnake(Point currentSeg, int currentDir, int segIndex) {
        Point preceedingSeg = new Point(0, 0);
        boolean validPoint;

        switch (currentDir) {
            case Snake.NORTH:
                preceedingSeg.setX(currentSeg.getX());
                preceedingSeg.setY(currentSeg.getY() + 1);
                break;
        
            case Snake.EAST:
                preceedingSeg.setX(currentSeg.getX() - 1);
                preceedingSeg.setY(currentSeg.getY());
                break;

            case Snake.SOUTH:
                preceedingSeg.setX(currentSeg.getX());
                preceedingSeg.setY(currentSeg.getY() - 1);
                break;

            case Snake.WEST:
                preceedingSeg.setX(currentSeg.getX() + 1);
                preceedingSeg.setY(currentSeg.getY());
                break;
        }

        if (preceedingSeg.getX() >= 0 &&
            preceedingSeg.getX() < board.length &&
            preceedingSeg.getY() >= 0 &&
            preceedingSeg.getY() < board[0].length) {
            
            validPoint = true;
        }
        else {
            validPoint = false;
        }

        if (segIndex < length && validPoint && !snakeMade) {
            board[preceedingSeg.getY()][preceedingSeg.getX()] = " o ";
            if (preceedingSeg.equals(futureHead())) {
                bitesBody = true;
            }

            int i;
            for (i = turnPoints.size() - 1; i >= 0; i--) {
                if (preceedingSeg.equals(turnPoints.get(i))) {
                    break;
                }
            }

            if (i >= 0 && preceedingSeg.equals(turnPoints.get(i))) {
                bendSnake(preceedingSeg, turnDirections.get(i), segIndex + 1);
            }
            else {
                bendSnake(preceedingSeg, currentDir, segIndex + 1);
            }
        }
        else if (segIndex >= length && validPoint && !snakeMade) {
            board[preceedingSeg.getY()][preceedingSeg.getX()] = " _ ";
            if (turnPoints.size() > 0 && preceedingSeg.equals(turnPoints.get(0))) {
                turnPoints.remove(0);
                turnDirections.remove(0);
            }
            snakeMade = true;
        }
    }

    private Point futureHead() {
        Point futureHead = new Point(0, 0);

        switch (direction) {
            case Snake.NORTH:
                futureHead.setY(head.getY() - 1);
                futureHead.setX(head.getX());
                break;
            
            case Snake.EAST:
                futureHead.setY(head.getY());
                futureHead.setX(head.getX() + 1);
                break;

            case Snake.SOUTH:
                futureHead.setY(head.getY() + 1);
                futureHead.setX(head.getX());
                break;

            case Snake.WEST:
                futureHead.setY(head.getY());
                futureHead.setX(head.getX() - 1);
                break;
        }

        return futureHead;
    }

    public boolean willBiteBody() {
        return this.bitesBody;
    }
}