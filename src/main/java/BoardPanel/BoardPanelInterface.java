package BoardPanel;

import java.awt.*;
import Tile.Tile;

public interface BoardPanelInterface {

    /**
     * Resets the board and clears away all tiles
     */
    void clear();

    /**
     * Checks possibility of placing a tile at the coordinates
     * @param type What figure needs to be placed
     * @param x Coordinate at X-axis
     * @param y Coordinate at Y-axis
     * @param rotation Rotation of tile
     * @return Is position on those coordinates valid or not
     */
    Boolean isValidAndEmpty(Tile type, Integer x, Integer y, Integer rotation);

    /**
     * Adds a tile on game board. Can overwrite existing tiles.
     * @param type What figure needs to be placed
     * @param x Coordinate at X-axis
     * @param y Coordinate at Y-axis
     * @param rotation Rotation of tile
     */
    void addPiece(Tile type, Integer x, Integer y, Integer rotation);

    /**
     * Checks all lines if there are any ones cleared and removes them from game
     * @return Amount of lines that were cleared
     */
    Integer checkLines();

    /**
     * Checks if line is full
     * @param line Index of row to check
     * @return Is current row full or not
     */
    Boolean checkLine(Integer line);

    /**
     * Check if the tile is already occupied
     * @param x Coordinate at X-axis to check
     * @param y Coordinate at Y-axis to check
     * @return Is current position occupied or not
     */
    Boolean isOccupied(Integer x, Integer y);

    /**
     * Sets a tile on required column and row on game board
     * @param x The column where tile needs to be placed
     * @param y The row where tile needs to be placed
     * @param type Type of tile to set
     */
    void setTile(Integer x, Integer y, Tile type);

    /**
     * Gets tile by it's column and row
     * @param x Column
     * @param y Row
     * @return Tile placed at requested column and row
     */
    Tile getTile(Integer x, Integer y);

    /**
     * Element for drawing all elements of game
     * @param g
     */
    void paintComponent(Graphics g);

    /**
     * Draws specific tile on board
     * @param type Type of tile to draw
     * @param x The column where to draw a tile
     * @param y The row where to draw a tile
     * @param g Graphics object
     */
    void drawTile(Tile type, Integer x, Integer y, Graphics g);

    /**
     * Draw a tile on board setting color for tile
     * @param base Base color for tile
     * @param light Color for lightning of tile
     * @param dark Color for shadow of tile
     * @param x Column
     * @param y Row
     * @param g Graphics object
     */
    void drawTile(Color base, Color light, Color dark, Integer x, Integer y, Graphics g);
}
