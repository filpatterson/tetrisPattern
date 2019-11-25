package Tile;

import BoardPanel.BoardPanelSettings;

import java.awt.*;

/**
 *      The {@code Tile} shows properties of pieces that are used in game, their types and possible actions.
 */
public enum Tile implements TileInterface {
    TypeI(new Color(BoardPanelSettings.COLOR_MIN, BoardPanelSettings.COLOR_MAX, BoardPanelSettings.COLOR_MAX), 4, 4, 1, new Boolean[][] {
            {
                    false, false, false, false,
                    true,  true,  true,  true,
                    false, false, false, false,
                    false, false, false, false
            },
            {
                    false, false, true, false,
                    false, false, true, false,
                    false, false, true, false,
                    false, false, true, false
            },
            {
                    false, false, false, false,
                    false, false, false, false,
                    true,  true,  true,  true,
                    false, false, false, false
            },
            {
                    false, true, false, false,
                    false, true, false, false,
                    false, true, false, false,
                    false, true, false, false
            }
    }),

    TypeJ(new Color(BoardPanelSettings.COLOR_MIN, BoardPanelSettings.COLOR_MIN, BoardPanelSettings.COLOR_MAX), 3, 3, 2, new Boolean[][] {
            {
                    true,  false, false,
                    true,  true,  true,
                    false, false, false
            },
            {
                    false, true,  true,
                    false, true,  false,
                    false, true,  false
            },
            {
                    false, false, false,
                    true,  true,  true,
                    true,  true,  true
            },
            {
                    false, true,  false,
                    false, true,  false,
                    true,  true,  false,
            }
    }),

    TypeL(new Color(BoardPanelSettings.COLOR_MAX, 127, BoardPanelSettings.COLOR_MIN), 3, 3, 2, new Boolean[][] {
            {
                    false, false, true,
                    true,  true,  true,
                    false, false, false
            },
            {
                    false, true,  false,
                    false, true,  false,
                    false, true,  true
            },
            {
                    false, false, false,
                    true,  true,  true,
                    true,  false, false
            },
            {
                    true,  true,  false,
                    false, true,  false,
                    false, true,  false
            }
    }),

    TypeO(new Color(BoardPanelSettings.COLOR_MAX, BoardPanelSettings.COLOR_MAX, BoardPanelSettings.COLOR_MIN), 2, 2, 2, new Boolean[][] {
            {
                    true,  true,
                    true,  true
            },
            {
                    true,  true,
                    true,  true
            },
            {
                    true,  true,
                    true,  true
            },
            {
                    true,  true,
                    true,  true
            }
    }),

    TypeS(new Color(BoardPanelSettings.COLOR_MIN, BoardPanelSettings.COLOR_MAX, BoardPanelSettings.COLOR_MIN), 3, 3, 2, new Boolean[][] {
            {
                    false, true,  true,
                    true,  true,  false,
                    false, false, false
            },
            {
                    false, true,  false,
                    false, true,  true,
                    false, false, true
            },
            {
                    false, false, false,
                    false, true,  true,
                    true,  true,  false
            },
            {
                    true,  false, false,
                    true,  true,  false,
                    false, true,  false
            }
    }),

    TypeT(new Color(128, BoardPanelSettings.COLOR_MIN, 128), 3, 3, 2, new Boolean[][] {
            {
                    false, true,  false,
                    true,  true,  true,
                    false, false, false
            },
            {
                    false, true,  false,
                    false, true,  true,
                    false, true,  false
            },
            {
                    false, false, false,
                    true,  true,  true,
                    false, true,  false
            },
            {
                    false, true,  false,
                    true,  true,  false,
                    false, true,  false,
            }
    }),

    TypeZ(new Color(BoardPanelSettings.COLOR_MAX, BoardPanelSettings.COLOR_MIN, BoardPanelSettings.COLOR_MIN), 3, 3, 2, new Boolean[][] {
            {
                    true,  true,  false,
                    false, true,  true,
                    false, false, false
            },
            {
                    false, false, true,
                    false, true,  true,
                    false, true,  false
            },
            {
                    false, false, false,
                    true,  true,  false,
                    false, true,  true
            },
            {
                    false, true,  false,
                    true,  true,  false,
                    true,  false, false
            }
    });

    /**
     * Base color for tiles
     */
    private Color baseColor;

    /**
     * Color of light shading for tile
     */
    private Color lightColor;

    /**
     * Color for shadow shading for file
     */
    private Color shadowColor;

    /**
     * Column that current tile spawns in
     */
    private Integer spawnColumn;

    /**
     * Row that current tile spawns in
     */
    private Integer spawnRow;

    /**
     * Dimensions of the array for this tile
     */
    private Integer dimension;

    /**
     * Amount of rows in current tile
     */
    private Integer rows;

    /**
     * Amount of rows in current tile
     */
    private Integer columns;

    /**
     * Pieces of tile.
     */
    private Boolean[][] tiles;

    Tile(Color color, Integer dimension, Integer columns, Integer rows, Boolean[][] tiles) {
        this.baseColor = color;
        this.lightColor = color.brighter();
        this.shadowColor = color.darker();
        this.dimension = dimension;
        this.tiles = tiles;
        this.columns = columns;
        this.rows = rows;

        this.spawnColumn = 5 - (dimension >> 1);
        this.spawnRow = getTopInsert(0);
    }

    public Color getBaseColor(){ return baseColor; }

    public Color getLightColor() { return lightColor; }

    public Color getShadowColor() { return shadowColor; }

    public Integer getDimension() { return dimension; }

    public Integer getSpawnColumn() { return spawnColumn; }

    public Integer getSpawnRow() { return spawnRow; }

    public Integer getRows() { return rows; }

    public Integer getColumns() { return columns; }

    public Boolean isTile(Integer x, Integer y, Integer rotation) { return tiles[rotation][y * dimension + x]; }

    public Integer getLeftInsert(Integer rotation) {
        // come from left to right until program finds a tile
        for(int x = 0; x < dimension; x++)
            for(int y = 0; y < dimension; y++)
                if(isTile(x, y, rotation))
                    return x;

        return -1;
    }

    public Integer getRightInsert(Integer rotation) {
         // come from right to left until program finds a tile
        for(int x = dimension - 1; x >= 0; x--)
            for(int y = 0; y < dimension; y++)
                if(isTile(x, y, rotation))
                    return dimension - x;

        return -1;
    }

    public Integer getTopInsert(Integer rotation) {
        // loop from top to bottom until is found a tile
        for(int y = 0; y < dimension; y++)
            for(int x = 0; x < dimension; x++)
                if(isTile(x, y, rotation))
                    return y;

        return -1;
    }

    public Integer getBottomInsert(Integer rotation) {
         // loop from bottom to top until is found a tile
        for(int y = dimension - 1; y >= 0; y--)
            for(int x = 0; x < dimension; x++)
                if(isTile(x, y, rotation))
                    return dimension - y;

        return -1;
    }
}