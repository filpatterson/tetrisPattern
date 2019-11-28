package Tetris;

import BoardPanel.BoardPanelService;
import BoardPanel.BoardPanelSettings;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Clock.Clock;
import java.util.Random;
import Tile.Tile;

public class Tetris extends TetrisSettings implements TetrisInterface {
    private TetrisSettings game = new Tetris();

    public void startGame() {
        //initialize random number generator
        game.setRandom(new Random());
        game.setNewGame(true);
        game.setGameSpeed(1.0f);

        //setup the timer for preventing game start before pressing Enter
        game.setLogicTimer(new Clock(game.getGameSpeed()));
        game.getLogicTimer().setPaused();

        while(true) {
            //get the time that the frame started
            Long start = System.nanoTime();

            //update the logic timer
            game.getLogicTimer().update();

            //if there came a cycle of timer then game be updated and tile can be moved down
            if(game.getLogicTimer().hasElapsedCycle()) {
                updateGame();
            }

            if(game.getDropCooldown() > 0) {
                game.setDropCooldown(game.getDropCooldown() - 1);
            }

            //render game
            renderGame();

            Long delta = (System.nanoTime() - start) / 1000000L;
            if(delta < getFrameTime()){
                try{
                    Thread.sleep(getFrameTime() - delta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

     public void updateGame(){
        if(game.getBoardPanelService().isValidAndEmpty(game.getTile(), game.getCurrentColumn(),
                game.getCurrentRow() + 1, game.getCurrentRotation())) {
            //increment the tile down if it can be done
            game.setCurrentRow(game.getCurrentRow() + 1);
        } else {
            //if bottom of board is reached or landed on another piece so needs to be added new tile to the board
            game.getBoardPanelService().addPiece(game.getTile(), game.getCurrentColumn(), game.getCurrentRow(),
                    game.getCurrentRotation());

            //game checks if there are cleared lines after setting tile and calculates score for player
            Integer cleared = game.getBoardPanelService().checkLines();
            if(cleared > 0)
                game.setScore(game.getScore() + 50 << cleared);

            //we need to make game harder by slightly increasing speed of next tile and update game's timer
            game.setGameSpeed(game.getGameSpeed() + 0.035f);
            game.getLogicTimer().setCyclesPerSecond(game.getGameSpeed());
            game.getLogicTimer().reset();

            //game needs small cooldown for creating time for gamer to react over new tile and where he/she can place it
            game.setDropCooldown(25);

            //game needs visual representation of increasing difficulty and for this is used 'level'
            game.setLevel((int)(game.getGameSpeed() * 1.7f));

            //spawn a new tile to control
            spawnTile();
        }
    }

     public void renderGame(){
        game.getBoardPanelService().repaint();
        game.getSide().repaint();
    }

     public void resetGame(){
        game.setLevel(1);
        game.setScore(0);
        game.setGameSpeed(1.0f);
        game.setTile(Tile.values()[getRandom().nextInt(getTypeCount())]);
        game.setNewGame(false);
        game.setGameOver(false);
        game.getBoardPanelService().clear();
        game.getLogicTimer().reset();
        game.getLogicTimer().setCyclesPerSecond(game.getGameSpeed());
        spawnTile();
    }

     public void spawnTile(){
        //reset position and rotation for spawn of new tile, pick the next tile to work with
        game.setTile(game.getNextTile());
        game.setCurrentColumn(game.getTile().getSpawnColumn());
        game.setCurrentRow(game.getTile().getSpawnRow());
        game.setCurrentRotation(0);
        game.setNextTile(Tile.values()[getRandom().nextInt(getTypeCount())]);

        //if spawn point is invalid then it means that there is no place for spawn and gamer lost
        if(!game.getBoardPanelService().isValidAndEmpty(game.getTile(), game.getCurrentColumn(), game.getCurrentRow(), game.getCurrentRotation())){
            game.setGameOver(true);
            game.getLogicTimer().setPaused(true);
        }
    }

     public void rotateTile(Integer newRotation){
        //sometimes there is need of moving tile so that it will not clip out from board
        Integer newColumn = game.getCurrentColumn();
        Integer newRow = game.getCurrentRow();

        //there is need in getting amount of free space from all sides of tile
        Integer left = game.getTile().getLeftInsert(newRotation);
        Integer right = game.getTile().getRightInsert(newRotation);
        Integer bottom = game.getTile().getBottomInsert(newRotation);
        Integer top = game.getTile().getTopInsert(newRotation);

        //if tile is too far right or left, then move it for evading clipping out from the game board
        if(game.getCurrentColumn() < -left)
            newColumn -= game.getCurrentColumn() - left;
        else if(game.getCurrentColumn() + game.getTile().getDimension() - right > BoardPanelSettings.COL_COUNT)
            newColumn -= (game.getCurrentColumn() + game.getTile().getDimension() - right)
                    - BoardPanelSettings.COL_COUNT + 1;

        //if tile is too far bottom or top, then move it for evading clipping out from the game board
        if(game.getCurrentRow() < -top)
            newRow -= game.getCurrentRow() - top;
        else if(game.getCurrentRow() + game.getTile().getDimension() - bottom >= BoardPanelSettings.ROW_COUNT)
            newRow -= (game.getCurrentRow() + game.getTile().getDimension() - bottom)
                    - BoardPanelSettings.ROW_COUNT + 1;

        //check to see if new position is acceptable. If it is, then update position and rotation of piece
        if(game.getBoardPanelService().isValidAndEmpty(game.getTile(), newColumn, newRow, newRotation)){
            game.setCurrentRotation(newRotation);
            game.setCurrentRow(newRow);
            game.setCurrentColumn(newColumn);
        }
    }

    public Boolean isPaused(){ return game.getPaused(); }

    public Boolean isGameOver() { return game.getGameOver(); }

    public Boolean isNewGame() { return game.getNewGame(); }

    public Integer getScore() { return game.getScore(); }

    public Integer getLevel() { return game.getLevel(); }

    public Tile getNextTile() { return game.getNextTile(); }

    public Integer getTileColumn() { return game.getCurrentColumn(); }

    public Integer getTileRow() { return game.getCurrentRow(); }

    public Integer getTileRotation() { return game.getCurrentRotation(); }

    private Tetris() {
        //set basic properties of window
        super("Tetris");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        //initialize BoardPanel and SidePanel
        game.setBoardPanelService(new BoardPanelService(this));
        game.setSide(new SidePanel(this));

        //add BoardPanel and SidePanel instances to the window
        add(game.getBoardPanelService(), BorderLayout.CENTER);
        add(game.getSidePanel(), BorderLayout.EAST);

        //add keyListener for controlling game and make it all work
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    //all cases before making action will make check if there is Pause

                    //drop tile when pressed (speeds up game timer)
                    case KeyEvent.VK_S:
                        if(!isPaused() && game.getDropCooldown() == 0)
                            game.getLogicTimer().setCyclesPerSecond(25.0f);
                        break;

                    //move tile left if there is free space from the left side
                    case KeyEvent.VK_A:
                        if(!isPaused() && game.getBoardPanelService().isValidAndEmpty(game.getTile(),
                                                                                        game.getCurrentColumn() - 1,
                                                                                        game.getCurrentRow(),
                                                                                        game.getCurrentRotation()))
                            game.setCurrentColumn(game.getCurrentColumn() - 1);
                        break;

                    //move tile right if there is free space from the right side
                    case KeyEvent.VK_D:
                        if(!isPaused() && game.getBoardPanelService().isValidAndEmpty(game.getTile(),
                                                                                        game.getCurrentColumn() + 1,
                                                                                        game.getCurrentRow(),
                                                                                        game.getCurrentRotation()))
                            game.setCurrentColumn(game.getCurrentColumn() + 1);
                        break;

                    //rotate tile anticlockwise
                    case KeyEvent.VK_Q:
                        if(!isPaused())
                            rotateTile((game.getCurrentRotation() == 0) ? 3 : game.getCurrentRotation() - 1);
                        break;

                    //rotate tile clockwise
                    case KeyEvent.VK_E:
                        if(!isPaused())
                            rotateTile((game.getCurrentRotation() == 0) ? 3 : game.getCurrentRotation() + 1);
                        break;

                    //pause game if game is not over or not started
                    case KeyEvent.VK_P:
                        if(!game.getGameOver() && !game.getNewGame()) {
                            game.setPaused(!game.getPaused());
                            getLogicTimer().setPaused(game.getPaused());
                        }
                        break;

                    //start game (if it is game over or new game state
                    case KeyEvent.VK_ENTER:
                        if(game.getGameOver() || game.getNewGame())
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
                        game.getLogicTimer().setCyclesPerSecond(game.getGameSpeed());
                        game.getLogicTimer().reset();
                        break;
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.startGame();
    }
}
