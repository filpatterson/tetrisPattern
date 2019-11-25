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
    Integer getDimension();

    /**
     * Gets spawn column of tile
     * @return Spawn column
     */
    Integer getSpawnColumn();

    /**
     * Gets spawn row of tile
     * @return Spawn row
     */
    Integer getSpawnRow();

    /**
     * Gets amount of rows in tile
     * @return Amount of rows in tile
     */
    Integer getRows();

    /**
     * Gets amount of columns in tile
     * @return Amount of columns in tile
     */
    Integer getColumns();

    /**
     * Checks if chosen coordinates and rotation contain a tile
     * @param x Coordinate on X-axis
     * @param y Coordinate on Y-axis
     * @param rotation Rotation to check in
     * @return Is there a tile
     */
    Boolean isTile(Integer x, Integer y, Integer rotation);

    /**
     * Shows amount of empty columns from the left side
     * @param rotation Rotation
     * @return Amount of free columns from the left side
     */
    Integer getLeftInsert(Integer rotation);

    /**
     * Shows amount of empty columns from the right side
     * @param rotation Rotation
     * @return Amount of free columns from the right side
     */
    Integer getRightInsert(Integer rotation);

    /**
     * Shows amount of empty rows from the top
     * @param rotation Rotation
     * @return Amount of free rows from the top
     */
    Integer getTopInsert(Integer rotation);

    /**
     * Shows amount of empty rows from the bottom
     * @param rotation Rotation
     * @return Amount of free rows from the bottom
     */
    Integer getBottomInsert(Integer rotation);
}
