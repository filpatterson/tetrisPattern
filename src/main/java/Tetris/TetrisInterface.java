package Tetris;

import Tile.Tile;

public interface TetrisInterface {
    /**
     * Starts the game and makes it running. Initializes all and enters the game loop
     */
    void startGame();

    /**
     * Handles game logic and updates its condition
     */
    void updateGame();

    /**
     * Makes repaint of main and side panels
     */
    void renderGame();

    /**
     * Resets all variables of game when starting the new one
     */
    void resetGame();

    /**
     * Spawns a new Tile. Resets all variables for "current tile" to their defaults
     */
    void spawnTile();

    /**
     * Makes rotation of tile and checks if it is possible considering other tiles and edges of game
     * @param newRotation New value for rotation of figure
     */
    void rotateTile(int newRotation);

    /**
     * Checks if game is paused
     * @return Is game paused or not
     */
    boolean isPaused();

    /**
     * Checks if game is over
     * @return Is game over or not
     */
    boolean isGameOver();

    /**
     * Checks if this is new game or not
     * @return Is this new game or not
     */
    boolean isNewGame();

    /**
     * Get current level of game
     * @return Current level of game
     */
    int getLevel();

    /**
     * Get next tile that will be spawned
     * @return Next tile that will spawn
     */
    Tile getNextTile();

    /**
     * Get current column of tile
     * @return Current column of tile
     */
    int getTileColumn();

    /**
     * Get current row of tile
     * @return Current row of tile
     */
    int getTileRow();

    /**
     * Get current rotation of tile
     * @return Current rotation of tile
     */
    int getTileRotation();
}
