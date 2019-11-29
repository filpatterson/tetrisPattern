package Tile;

import java.awt.*;

public interface TileInterface {
    /**
     * Gets base color of tile
     * @return Base color of tile
     */
    Color getBaseColor();

    /**
     * Gets light shading color
     * @return Light color
     */
    Color getLightColor();

    /**
     * Gets shadow shading color
     * @return Shadow color
     */
    Color getShadowColor();

    /**
     * Gets dimension of tile
     * @return Dimension of tile
     */
    int getDimension();

    /**
     * Gets spawn column of tile
     * @return Spawn column
     */
    int getSpawnColumn();

    /**
     * Gets spawn row of tile
     * @return Spawn row
     */
    int getSpawnRow();

    /**
     * Gets amount of rows in tile
     * @return Amount of rows in tile
     */
    int getRows();

    /**
     * Gets amount of columns in tile
     * @return Amount of columns in tile
     */
    int getColumns();

    /**
     * Checks if chosen coordinates and rotation contain a tile
     * @param x Coordinate on X-axis
     * @param y Coordinate on Y-axis
     * @param rotation Rotation to check in
     * @return Is there a tile
     */
    boolean isTile(int x, int y, int rotation);

    /**
     * Shows amount of empty columns from the left side
     * @param rotation Rotation
     * @return Amount of free columns from the left side
     */
    int getLeftInsert(int rotation);

    /**
     * Shows amount of empty columns from the right side
     * @param rotation Rotation
     * @return Amount of free columns from the right side
     */
    int getRightInsert(int rotation);

    /**
     * Shows amount of empty rows from the top
     * @param rotation Rotation
     * @return Amount of free rows from the top
     */
    int getTopInsert(int rotation);

    /**
     * Shows amount of empty rows from the bottom
     * @param rotation Rotation
     * @return Amount of free rows from the bottom
     */
    int getBottomInsert(int rotation);
}
