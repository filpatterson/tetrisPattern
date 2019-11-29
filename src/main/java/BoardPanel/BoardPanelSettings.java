package BoardPanel;

import java.awt.*;

public class BoardPanelSettings {
    /**
     * Limits for color component values for tiles
     * Minimal color component that can be used for setting shading and light
     */
    public static final int COLOR_MIN = 35;

    /**
     * Maximum color component that can be used for setting shading and light
     */
    public static final int COLOR_MAX = 255 - COLOR_MIN;

    /**
     * Limit of width for border around the game board
     */
    public static final int BORDER_WIDTH = 5;

    /**
     * Amount of columns on the game board
     */
    public static final int COL_COUNT = 10;

    /**
     * Amount of rows on the game board
     */
    public static final int VISIBLE_ROW_COUNT = 20;

    /**
     * Game needs place for spawning new elements out of view. For this game needs some hidden rows.
     * Amount of hidden from player's view rows.
     */
    public static final int HIDDEN_ROW_COUNT = 2;

    /**
     * Total amount of all rows on game field
     */
    public static final int ROW_COUNT = VISIBLE_ROW_COUNT + HIDDEN_ROW_COUNT;

    /**
     * Any figure requires setting size of it's drawing in pixels.
     * Amount of pixels for drawing tile on screen
     */
    public static final int TILE_SIZE_PIXELS = 24;

    /**
     * Tiles requires shading (looks better).
     * Amount of pixels for shading
     */
    public static final int SHADE_SIZE_PIXELS = 4;

    /**
     * X-axis center of game board
     */
    public static final int CENTER_X = COL_COUNT * TILE_SIZE_PIXELS / 2;

    /**
     * Y-axis center of game board
     */
    public static final int CENTER_Y = VISIBLE_ROW_COUNT * TILE_SIZE_PIXELS / 2;

    /**
     * Total height of the game panel
     */
    public static final int PANEL_WIDTH = COL_COUNT * TILE_SIZE_PIXELS + BORDER_WIDTH * 2;

    /**
     * Total width of the game panel
     */
    public static final int PANEL_HEIGHT = VISIBLE_ROW_COUNT * TILE_SIZE_PIXELS + BORDER_WIDTH * 2;

    /**
     * The larger font to display
     */
    public static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 16);

    /**
     * The smaller font to display
     */
    public static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);
}
