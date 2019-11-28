package SidePanel;

import BoardPanel.BoardPanelSettings;
import Tetris.Tetris;

import javax.swing.*;
import java.awt.*;

public class SidePanelSettings extends JPanel {
    /**
     * Dimension of each tile on preview for new element
     */
    static final Integer TILE_SIZE = BoardPanelSettings.TILE_SIZE_PIXELS >> 1;

    /**
     * Width of shading for new element
     */
    static final Integer SHADE_WIDTH = BoardPanelSettings.SHADE_SIZE_PIXELS >> 1;

    /**
     * Size of piece from which will be formed preview box (looks like a grid)
     */
    static final Integer PIECE_SIZE = 5;

    /**
     * The center x of the next tile preview box
     */
    static final Integer SQUARE_CENTER_X = 130;

    /**
     * The center y of the next tile preview box
     */
    static final Integer SQUARE_CENTER_Y = 65;

    /**
     * Size of preview box
     */
    static final Integer PREVIEW_SIZE = (TILE_SIZE * PIECE_SIZE >> 1);

    /**
     * Offset that is used for separating small entities of menu
     */
    static final Integer SMALL_INSET = 20;

    /**
     * Offset for separating big elements of menu
     */
    static final Integer LARGE_INSET = 40;

    /**
     * Y coordinate for stats elements
     */
    static final Integer STATS_INSET = 175;

    /**
     * Y coordinate for controls elements
     */
    static final Integer CONTROLS_INSET = 300;

    /**
     * Offset for separating strings
     */
    static final Integer TEXT_INSET = 25;

    /**
     * Small font
     */
    static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 11);

    /**
     * Big font
     */
    static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 13);

    /**
     * Color for text and preview box
     */
    static final Color DRAW_COLOR = new Color(128, 192, 128);

    Tetris tetris;
}
