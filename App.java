import java.util.HashSet;
import java.util.Random;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class App {
    
    static boolean turnPointAdded = true;
    static boolean paused = false;
    static boolean retry = true;

    public static void main (String[] args) {
        Random rnd = new Random();

        final int BOARD_SIZE = 40;
        final int BOARD_SQUARE_SIZE = 20;
        final int BOARD_BORDER_WIDTH = 2;
        final int BOARD_INSET = 24;
        final int GENERATION_OFFSET = 10;
        
        TurningPoint turnPoint = new TurningPoint(0, 0, Snake.NORTH);

        JFrame app = new JFrame();
        app.setTitle("Snake Game");
        app.setResizable(false);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setLayout(new GridBagLayout());
        app.setBackground(new Color(247, 247, 247));
        Container contentPane = app.getContentPane();
        JLayeredPane layeredPane = app.getLayeredPane();

        JPanel boardPanel = new JPanel(null);
        boardPanel.setPreferredSize(new Dimension(BOARD_SQUARE_SIZE * BOARD_SIZE + 2 * BOARD_BORDER_WIDTH,
                                                  BOARD_SQUARE_SIZE * BOARD_SIZE + 2 * BOARD_BORDER_WIDTH));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, BOARD_BORDER_WIDTH));
        boardPanel.setBackground(new Color(247, 247, 247));

        Snake player = new Snake(BOARD_SIZE, BOARD_SIZE, boardPanel, BOARD_SQUARE_SIZE);

        Food food = new Food();
        food.setSize(BOARD_SQUARE_SIZE, BOARD_SQUARE_SIZE);
        boardPanel.add(food);

        Action setNorth = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!paused && player.getDirection() != Snake.NORTH && player.getDirection() != Snake.SOUTH && turnPointAdded) {
                    turnPoint.setX(player.getHead().getX());
                    turnPoint.setY(player.getHead().getY());
                    turnPoint.setDirection(player.getDirection());
                    turnPointAdded = false;
                    player.setDirection(Snake.NORTH);
                }
            } 
        };
        Action setEast = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!paused && player.getDirection() != Snake.EAST && player.getDirection() != Snake.WEST && turnPointAdded) {
                    turnPoint.setX(player.getHead().getX());
                    turnPoint.setY(player.getHead().getY());
                    turnPoint.setDirection(player.getDirection());
                    turnPointAdded = false;
                    player.setDirection(Snake.EAST);
                }
            } 
        };
        Action setSouth = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!paused && player.getDirection() != Snake.NORTH && player.getDirection() != Snake.SOUTH && turnPointAdded) {
                    turnPoint.setX(player.getHead().getX());
                    turnPoint.setY(player.getHead().getY());
                    turnPoint.setDirection(player.getDirection());
                    turnPointAdded = false;
                    player.setDirection(Snake.SOUTH);
                }
            } 
        };
        Action setWest = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!paused && player.getDirection() != Snake.EAST && player.getDirection() != Snake.WEST && turnPointAdded) {
                    turnPoint.setX(player.getHead().getX());
                    turnPoint.setY(player.getHead().getY());
                    turnPoint.setDirection(player.getDirection());
                    turnPointAdded = false;
                    player.setDirection(Snake.WEST);
                }
            } 
        };
        
        boardPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "setNorth");
        boardPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "setEast");
        boardPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "setSouth");
        boardPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "setWest");
        boardPanel.getActionMap().put("setNorth", setNorth);
        boardPanel.getActionMap().put("setEast", setEast);
        boardPanel.getActionMap().put("setSouth", setSouth);
        boardPanel.getActionMap().put("setWest", setWest);

        GridBagConstraints boardPanelConstraints = new GridBagConstraints();
        boardPanelConstraints.gridx = 0;
        boardPanelConstraints.gridy = 0;
        boardPanelConstraints.weightx = 1.0;
        boardPanelConstraints.weighty = 1.0;
        boardPanelConstraints.fill = GridBagConstraints.BOTH;
        boardPanelConstraints.insets = new Insets(BOARD_INSET, BOARD_INSET, BOARD_INSET, BOARD_INSET);
        contentPane.add(boardPanel, boardPanelConstraints);

        JPanel modalPanel = new ModalPanel(new GridBagLayout());
        modalPanel.setBackground(Color.BLACK);
        modalPanel.setOpaque(false);
        modalPanel.setBounds(BOARD_INSET,
                             BOARD_INSET,
                             boardPanel.getPreferredSize().width,
                             boardPanel.getPreferredSize().height);

        JPanel centerBox = new ModalPanel(new GridBagLayout());
        centerBox.setOpaque(false);
        centerBox.setBackground(new Color(0, 0, 0, 0));

        // Add components to the modal panel
        JLabel modalInfoLabel = new JLabel("", SwingConstants.CENTER);
        Font modalInfoLabelFont = modalInfoLabel.getFont();
        modalInfoLabel.setFont(modalInfoLabelFont.deriveFont(64.0f));
        GridBagConstraints gameOverConstraints = new GridBagConstraints();
        gameOverConstraints.gridx = 0;
        gameOverConstraints.gridy = 0;
        gameOverConstraints.gridwidth = GridBagConstraints.REMAINDER;
        centerBox.add(modalInfoLabel, gameOverConstraints);

        JButton retryButton = new JButton("Retry");
        retryButton.setFocusPainted(false);
        retryButton.setOpaque(true);
        retryButton.setBackground(new Color(54, 165, 199));
        Font retryFont = retryButton.getFont();
        retryButton.setFont(retryFont.deriveFont(14.0f));
        GridBagConstraints retryButtonConstraints = new GridBagConstraints();
        retryButtonConstraints.gridx = 0;
        retryButtonConstraints.gridy = 1;
        retryButtonConstraints.weightx = 0.5;
        retryButtonConstraints.ipadx = 10;
        retryButtonConstraints.ipady = 10;
        retryButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        retryButtonConstraints.insets = new Insets(BOARD_INSET / 6, BOARD_INSET / 6, BOARD_INSET / 6, BOARD_INSET / 6);
        retryButton.addActionListener(e -> {
                retry = true;
            });
        centerBox.add(retryButton, retryButtonConstraints);

        JButton exitButton = new JButton("Exit");
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(true);
        exitButton.setBackground(new Color(54, 165, 199));
        Font exitFont = exitButton.getFont();
        exitButton.setFont(exitFont.deriveFont(14.0f));
        GridBagConstraints exitButtonConstraints = new GridBagConstraints();
        exitButtonConstraints.gridx = 1;
        exitButtonConstraints.gridy = 1;
        exitButtonConstraints.weightx = 0.5;
        exitButtonConstraints.ipadx = 10;
        exitButtonConstraints.ipady = 10;
        exitButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
        exitButtonConstraints.insets = new Insets(BOARD_INSET / 6, BOARD_INSET / 6, BOARD_INSET / 6, BOARD_INSET / 6);
        exitButton.addActionListener(e -> {
                app.dispose();
                System.exit(0);
            });
        centerBox.add(exitButton, exitButtonConstraints);
        
        modalPanel.add(centerBox);
        layeredPane.add(modalPanel, JLayeredPane.MODAL_LAYER);
        modalPanel.setVisible(false);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints infoPanelConstraints = new GridBagConstraints();
        infoPanelConstraints.fill = GridBagConstraints.BOTH;
        infoPanelConstraints.weighty = 1.0;
        infoPanelConstraints.gridx = 1;
        infoPanelConstraints.gridy = 0;
        infoPanelConstraints.insets = new Insets(BOARD_INSET, 0, BOARD_INSET, BOARD_INSET);
        contentPane.add(infoPanel, infoPanelConstraints);

        ImageIcon pauseIcon = new ImageIcon("resources/pause.png", "pause button icon");
        pauseIcon.setImage(pauseIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        ImageIcon playIcon = new ImageIcon("resources/play.png", "play button icon");
        playIcon.setImage(playIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        JButton pauseButton = new JButton(pauseIcon);
        int size = Math.max(pauseButton.getPreferredSize().width,
                                  pauseButton.getPreferredSize().height);
        pauseButton.setPreferredSize(new Dimension(size, size));
        pauseButton.setFocusPainted(false);
        pauseButton.setOpaque(true);
        pauseButton.setBackground(new Color(54, 165, 199));
        GridBagConstraints pauseButtonConstraints = new GridBagConstraints();
        pauseButtonConstraints.gridx = 0;
        pauseButtonConstraints.gridy = 0;
        pauseButtonConstraints.insets = new Insets(0, 0, BOARD_INSET / 2, 0);
        pauseButton.addActionListener(e -> {
            paused = !paused;
            if (!paused) {
                pauseButton.setIcon(pauseIcon);
                modalPanel.setVisible(false);
                boardPanel.setEnabled(true);
            }
            else {
                pauseButton.setIcon(playIcon);
            }
        });
        infoPanel.add(pauseButton, pauseButtonConstraints);

        JLabel score = new JLabel("Score: 0");
        Font scoreFont = score.getFont();
        score.setFont(scoreFont.deriveFont(14.0f));
        GridBagConstraints scoreLabelConstraints = new GridBagConstraints();
        scoreLabelConstraints.gridx = 0;
        scoreLabelConstraints.gridy = 1;
        scoreLabelConstraints.anchor = GridBagConstraints.NORTHWEST;
        scoreLabelConstraints.weighty = 1;
        infoPanel.add(score, scoreLabelConstraints);

        app.pack();
        app.setLocationRelativeTo(null);
        app.setVisible(true);
        
        boolean exit = false;
        boolean spawnFood = false;
        long start;
        long end = 0L;
        while (true) {

            if (retry) {
                modalPanel.setVisible(false);
                boardPanel.setEnabled(true);
                infoPanel.setEnabled(true);
                pauseButton.setEnabled(true);
                int initialDirection = rnd.nextInt(4);
                int xcoord = rnd.nextInt(BOARD_SIZE - (GENERATION_OFFSET * 2)) + GENERATION_OFFSET;
                int ycoord = rnd.nextInt(BOARD_SIZE - (GENERATION_OFFSET * 2)) + GENERATION_OFFSET;
                Point initialHead = new Point(xcoord, ycoord);
                player.setHead(initialHead);
                player.setDirection(initialDirection);
                player.setLength(1);
                player.resetBiteBools();

                int foodxcoord = xcoord;
                int foodycoord = ycoord;
                do {
                    foodxcoord = rnd.nextInt(BOARD_SIZE - (GENERATION_OFFSET * 2)) + GENERATION_OFFSET;
                    foodycoord = rnd.nextInt(BOARD_SIZE - (GENERATION_OFFSET * 2)) + GENERATION_OFFSET;
                } while (foodxcoord == xcoord && foodycoord == ycoord);
                Point foodPoint = new Point(foodxcoord, foodycoord);
                food.setLocation(foodxcoord * BOARD_SQUARE_SIZE + BOARD_BORDER_WIDTH, foodycoord * BOARD_SQUARE_SIZE + BOARD_BORDER_WIDTH);
                player.setFood(foodPoint);

                exit = false;
                paused = false;
                spawnFood = false;
                end = 0L;
                retry = false;
            }

            if (!exit && !paused) {    
                start = System.nanoTime();
                if ((start - end) / 1000000 > (200 - player.getLength() * 2)) {
                    if (!turnPointAdded) {
                        player.addTurnPoint(new Point(turnPoint.getX(), turnPoint.getY()), turnPoint.getDirection());
                        turnPointAdded = true;
                    }

                    spawnFood = player.shiftHead();
                    HashSet<Point> snakePoints = player.makeSnake();
                    
                    if (player.willBiteBoard() || player.willBiteBody()) {
                        exit = true;
                    } 
                    else {
                        player.renderTo(boardPanel, BOARD_BORDER_WIDTH);
                    }

                    if (spawnFood) {
                        int xcoord;
                        int ycoord;
                        Point foodPoint;

                        do {
                            xcoord = rnd.nextInt(BOARD_SIZE);
                            ycoord = rnd.nextInt(BOARD_SIZE);
                            foodPoint = new Point(xcoord, ycoord);
                        } while (snakePoints.contains(foodPoint));

                        food.setLocation(xcoord * BOARD_SQUARE_SIZE + BOARD_BORDER_WIDTH, ycoord * BOARD_SQUARE_SIZE + BOARD_BORDER_WIDTH);
                        player.setFood(foodPoint);
                    }

                    end = start;
                }
                food.repaint();

                score.setText("Score: " + (player.getLength() - 1));
            }
            else {
                if (exit) {
                    modalInfoLabel.setForeground(Color.RED);
                    modalInfoLabel.setText("GAME OVER!");
                    infoPanel.setEnabled(false);
                    pauseButton.setEnabled(false);
                } else if (paused) {
                    modalInfoLabel.setForeground(Color.WHITE);
                    modalInfoLabel.setText("PAUSED");
                }
                modalPanel.setVisible(true);
                boardPanel.setEnabled(false);
            }
        }
    }
}