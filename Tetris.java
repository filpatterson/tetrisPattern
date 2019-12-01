package Tetris;

import BoardPanel.BoardPanelService;
import BoardPanel.BoardPanelSettings;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Clock.Clock;
import java.util.Random;
import Tile.Tile;
import SidePanel.SidePanel;

import javax.swing.*;

public class Tetris extends JFrame implements TetrisInterface {
    /**
     * How many milliseconds it takes per frame
     */
    private static final long FRAME_TIME = 1000L / 50L;

    /**
     * Amount of tiles that there can be
     */
    private static final int TYPE_COUNT = Tile.values().length;

    /**
     * BoardPanel instance
     */
    private BoardPanelService boardPanelService;

    /**
     * SidePanelInstance
     */
    private SidePanel side;

    /**
     * Is game over or not
     */
    private boolean isGameOver;

    /**
     * Is game paused or not
     */
    private boolean isPaused;

    /**
     * Checks if game was played (becomes false automatically when game starts
     */
    private boolean isNewGame;

    /**
     * Current level of game
     */
    private int level;

    /**
     * Current game score
     */
    private int score;

    /**
     * Random number generator used to make tile spawn randomly
     */
    private Random random;

    /**
     * Clock that handles the update logic
     */
    private Clock logicTimer;

    /**
     * Current tile
     */
    private Tile tile = null;

    /**
     * Next tile that will appear on screen
     */
    private Tile nextTile = null;

    /**
     * The current row of current tile
     */
    private int currentRow;

    /**
     * The current col of current tile
     */
    private int currentColumn;

    /**
     * The current rotation of tile
     */
    private int currentRotation;

    /**
     * Small amount of time that passes after spawn of tile and before its drop
     */
    private int dropCooldown;

    /**
     * Speed of game
     */
    private float gameSpeed;

    public void startGame() {
        //initialize random number generator
        random = new Random();
        isNewGame = true;
        gameSpeed = 1.0f;

        //setup the timer for preventing game start before pressing Enter
        logicTimer = new Clock(gameSpeed);
        logicTimer.setPaused(true);

        while(true) {
            //get the time that the frame started
            long start = System.nanoTime();

            //update the logic timer
            logicTimer.update();

            //if there came a cycle of timer then game be updated and tile can be moved down
            if(logicTimer.hasElapsedCycle()) {
                updateGame();
            }

            if(dropCooldown > 0) {
                dropCooldown--;
            }

            //render game
            renderGame();

            long delta = (System.nanoTime() - start) / 1000000L;
            if(delta < FRAME_TIME){
                try{
                    Thread.sleep(FRAME_TIME - delta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

     public void updateGame(){
        if(boardPanelService.isValidAndEmpty(tile, currentColumn, currentRow + 1, currentRotation)) {
            //increment the tile down if it can be done
            currentRow++;
        } else {
            //if bottom of board is reached or landed on another piece so needs to be added new tile to the board
            boardPanelService.addPiece(tile, currentColumn, currentRow, currentRotation);

            //game checks if there are cleared lines after setting tile and calculates score for player
            int cleared = boardPanelService.checkLines();
            if(cleared > 0)
                score = score + 50 << cleared;

            //we need to make game harder by slightly increasing speed of next tile and update game's timer
            gameSpeed += 0.035f;
            logicTimer.setCyclesPerSecond(gameSpeed);
            logicTimer.reset();

            //game needs small cooldown for creating time for gamer to react over new tile and where he/she can place it
            dropCooldown = 25;

            //game needs visual representation of increasing difficulty and for this is used 'level'
            level = (int)(gameSpeed * 1.7f);

            //spawn a new tile to control
            spawnTile();
        }
    }

     public void renderGame(){
        boardPanelService.repaint();
        side.repaint();
    }

     public void resetGame(){
        level = 1;
        score = 0;
        gameSpeed = 1.0f;
        nextTile = Tile.values()[random.nextInt(TYPE_COUNT)];
        isNewGame = false;
        isGameOver = false;
        boardPanelService.clear();
        logicTimer.reset();
        logicTimer.setCyclesPerSecond(gameSpeed);
        spawnTile();
    }

     public void spawnTile(){
        //reset position and rotation for spawn of new tile, pick the next tile to work with
        tile = nextTile;
        currentColumn = tile.getSpawnColumn();
        currentRow = tile.getSpawnRow();
        currentRotation = 0;
        nextTile = Tile.values()[random.nextInt(TYPE_COUNT)];

        //if spawn point is invalid then it means that there is no place for spawn and gamer lost
        if(!boardPanelService.isValidAndEmpty(tile, currentColumn, currentRow, currentRotation)){
            isGameOver = true;
            logicTimer.setPaused(true);
        }
    }

     public void rotateTile(int newRotation){
        //sometimes there is need of moving tile so that it will not clip out from board
        int newColumn = currentColumn;
        int newRow = currentRow;

        //there is need in getting amount of free space from all sides of tile
         int left = tile.getLeftInsert(newRotation);
         int right = tile.getRightInsert(newRotation);
         int top = tile.getTopInsert(newRotation);
         int bottom = tile.getBottomInsert(newRotation);

        //if tile is too far right or left, then move it for evading clipping out from the game board
         if(currentColumn < -left)
             newColumn -= currentColumn - left;
         else if(currentColumn + tile.getDimension() - right >= BoardPanelSettings.COL_COUNT)
             newColumn -= (currentColumn + tile.getDimension() - right) - BoardPanelSettings.COL_COUNT + 1;

        //if tile is too far bottom or top, then move it for evading clipping out from the game board
         if(currentRow < -top) {
             newRow -= currentRow - top;
         } else if(currentRow + tile.getDimension() - bottom >= BoardPanelSettings.ROW_COUNT) {
             newRow -= (currentRow + tile.getDimension() - bottom) - BoardPanelSettings.ROW_COUNT + 1;
         }

        //check to see if new position is acceptable. If it is, then update position and rotation of piece
         if(boardPanelService.isValidAndEmpty(tile, newColumn, newRow, newRotation)) {
             currentRotation = newRotation;
             currentRow = newRow;
             currentColumn = newColumn;
         }
    }

    //Singleton design pattern instance of Tetris
    public static Tetris instance = new Tetris();

    //Returns the instance of Tetris
    public static Tetris getInstance() {
        return instance;
    }

    public boolean isPaused(){ return isPaused; }

    public boolean isGameOver() { return isGameOver; }

    public boolean isNewGame() { return isNewGame; }

    public int getScore() { return score; }

    public int getLevel() { return level; }

    public Tile getNextTile() { return nextTile; }

    public int getTileColumn() { return currentColumn; }

    public int getTileRow() { return currentRow; }

    public int getTileRotation() { return currentRotation; }

    public Tile getTile() { return tile; }

    private Tetris() {
        //set basic properties of window
        super("tetris");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //initialize BoardPanel and SidePanel
        boardPanelService = new BoardPanelService(this);
        side = new SidePanel(this);

        //add BoardPanel and SidePanel instances to the window
        add(boardPanelService, BorderLayout.CENTER);
        add(side, BorderLayout.EAST);

        //add keyListener for controlling game and make it all work
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    //all cases before making action will make check if there is Pause

                    //drop tile when pressed (speeds up game timer)
                    case KeyEvent.VK_S:
                        if(!isPaused() && dropCooldown == 0)
                            logicTimer.setCyclesPerSecond(25.0f);
                        break;

                    //move tile left if there is free space from the left side
                    case KeyEvent.VK_A:
                        if(!isPaused() && boardPanelService.isValidAndEmpty(tile, currentColumn - 1, currentRow,
                                currentRotation))
                            currentColumn = currentColumn - 1;
                        break;

                    //move tile right if there is free space from the right side
                    case KeyEvent.VK_D:
                        if(!isPaused() && boardPanelService.isValidAndEmpty(tile, currentColumn + 1, currentRow,
                                currentRotation))
                            currentColumn = currentColumn + 1;
                        break;

                    //rotate tile anticlockwise
                    case KeyEvent.VK_Q:
                        if(!isPaused())
                            rotateTile((currentRotation == 0) ? 3 : currentRotation - 1);
                        break;

                    //rotate tile clockwise
                    case KeyEvent.VK_E:
                        if(!isPaused())
                            rotateTile((currentRotation == 3) ? 0 : currentRotation + 1);
                        break;

                    //pause game if game is not over or not started
                    case KeyEvent.VK_P:
                        if(!isGameOver && !isNewGame) {
                            isPaused = !isPaused;
                            logicTimer.setPaused(isPaused);
                        }
                        break;

                    //start game (if it is game over or new game state
                    case KeyEvent.VK_ENTER:
                        if(isGameOver || isNewGame)
                            resetGame();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch(e.getKeyCode()){
                    //when key is released set speed of the logic timer back to current game speed and clear cycles that
                    //might still been elapsed
                    case KeyEvent.VK_S:
                        logicTimer.setCyclesPerSecond(gameSpeed);
                        logicTimer.reset();
                        break;
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        //Singleton GUI
        Tetris tetris = Tetris.getInstance();
        tetris.startGame();
    }
}