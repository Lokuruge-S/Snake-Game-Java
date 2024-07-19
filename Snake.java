import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Snake {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    private int boardX;
    private int boardY;
    private JPanel boardPanel;
    private int squareSize;

    private Point head;
    private int direction;
    private int length;
    private Point food;

    private ArrayList<TurningPoint> turnPoints = new ArrayList<TurningPoint>();
    private ArrayList<SnakeSeg> snake = new ArrayList<SnakeSeg>();
    private boolean bitesBody = false;
    private boolean bitesBoard = false;

    public Snake (int boardX, int boardY, JPanel boardPanel, int squareSize) {
        //initialize the board parameter
        this.boardX = boardX;
        this.boardY = boardY;
        this.boardPanel = boardPanel;
        this.squareSize = squareSize;
    }

    public void setHead (Point head) {
        this.head = head;
    }

    public Point getHead () {
        return this.head;
    }

    public void setDirection (int direction) {
        if (direction >= Snake.NORTH && direction <= Snake.WEST) {
            this.direction = direction;
        }
        else {
            this.direction = Snake.NORTH;
        }
    }

    public int getDirection () {
        return this.direction;
    }

    public void setLength(int length) {
        this.length = length;

        //clear any existing components
        for (SnakeSeg seg : snake) {
            this.boardPanel.remove(seg);
        }
        snake.clear();

        //add the starting componenets to the board
        SnakeHead snakeHead = new SnakeHead();
        snakeHead.setSize(squareSize, squareSize);
        snake.add(snakeHead);
        this.boardPanel.add(snakeHead);
        for (int i = 1; i < this.length; i++) {
            SnakeBody seg = new SnakeBody();
            seg.setSize(squareSize, squareSize);
            snake.add(seg);
            this.boardPanel.add(seg);
        }
    }

    public int getLength() {
        return this.length;
    }

    private void grow() {
        this.length++;
        SnakeBody seg = new SnakeBody();
            seg.setSize(squareSize, squareSize);
            seg.setVisible(false);
            snake.add(seg);
            this.boardPanel.add(seg);
    }

    public void setFood(Point food) {
        this.food = food;
    }

    public boolean shiftHead() {
        int x;
        int y;

        switch (direction) {
            case Snake.NORTH:
                y = head.getY() - 1;
                if (y < 0) {
                    this.bitesBoard = true;
                }
                else {
                    head.setY(y);
                }
                break;
            
            case Snake.EAST:
                x = head.getX() + 1;
                if (x >= boardX) {
                    this.bitesBoard = true;
                }
                else {
                    head.setX(x);
                }
                break;

            case Snake.SOUTH:
                y = head.getY() + 1;
                if (y >= boardY) {
                    this.bitesBoard = true;
                }
                else {
                    head.setY(y);
                }
                break;

            case Snake.WEST:
                x = head.getX() - 1;
                if (x < 0) {
                    this.bitesBoard = true;
                } 
                else {
                    head.setX(x);
                }
                break;
        }

        if (head.equals(food)) {
            grow();
            return true;
        }
        else {
            return false;
        }
    }

    public void addTurnPoint(Point head, int turnDir) {
        turnPoints.add(new TurningPoint(head.getX(), head.getY(), turnDir));
    }

    public HashSet<Point> makeSnake() {
        HashSet<Point> snakePoints = new HashSet<Point>();

        boolean firstSeg = true;
        Point segPoint = new Point(head.getX(), head.getY());
        int currentDir = direction;
        int i = turnPoints.size() - 1;

        for (SnakeSeg seg : snake) {
            if (firstSeg) {
                seg.setDirection(currentDir);
                seg.pos = segPoint;
                firstSeg = false;
            } else {
                switch (currentDir) {
                    case Snake.NORTH:
                        segPoint = new Point(segPoint.getX(), segPoint.getY() + 1);
                        break;
                
                    case Snake.EAST:
                        segPoint = new Point(segPoint.getX() - 1, segPoint.getY());
                        break;

                    case Snake.SOUTH:
                        segPoint = new Point(segPoint.getX(), segPoint.getY() - 1);
                        break;

                    case Snake.WEST:
                        segPoint = new Point(segPoint.getX() + 1, segPoint.getY());
                        break;
                }
                seg.pos = segPoint;

                if (i >= 0 && segPoint.equals(turnPoints.get(i))) {
                    currentDir = turnPoints.get(i).getDirection();
                    i--;
                }

                if (segPoint.equals(head)) {
                    bitesBody = true;
                }
            }
            
            snakePoints.add(segPoint);
        }

        if (i >= 0) {
             turnPoints.remove(0);
        }

        return snakePoints;
    }

    public boolean willBiteBody() {
        return this.bitesBody;
    }

    public boolean willBiteBoard() {
        return this.bitesBoard;
    }

    public void resetBiteBools() {
        this.bitesBody = false;
        this.bitesBoard = false;
    }

    public void renderTo(JComponent panel, int panelBorderWidth) {
        for (SnakeSeg seg : snake) {
            seg.setLocation(seg.pos.getX() * squareSize + panelBorderWidth, seg.pos.getY() * squareSize + panelBorderWidth);
            seg.setVisible(true);
        }
    }
}