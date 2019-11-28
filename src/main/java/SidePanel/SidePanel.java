package SidePanel;

import BoardPanel.BoardPanelSettings;
import Tetris.Tetris;
import Tile.Tile;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends SidePanelSettings {
    /**
     * Constructor with establishing game settings and characteristics of panel
     * @param tetris Game entity
     */
    public SidePanel(Tetris tetris){
        this.tetris = tetris;
        setPreferredSize(new Dimension(200, BoardPanelSettings.PANEL_HEIGHT));
        setBackground(Color.BLACK);
    }

    /**
     * Paint all side panel for that game
     * @param g Graphics entity
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //set color for drawing
        g.setColor(DRAW_COLOR);

        //variable for handling Y position of string or element
        Integer offset;

        //stats info
        g.setFont(LARGE_FONT);
        g.drawString("STATS", SMALL_INSET, offset = STATS_INSET);
        g.setFont(SMALL_FONT);
        g.drawString("Level: " + tetris.getLevel(), LARGE_INSET, offset += TEXT_INSET);

        //controls field
        g.setFont(LARGE_FONT);
        g.drawString("Controls", SMALL_INSET, offset = CONTROLS_INSET);
        g.setFont(SMALL_FONT);
        g.drawString("A - Move Left", LARGE_INSET, offset += TEXT_INSET);
        g.drawString("D - Move Right", LARGE_INSET, offset += TEXT_INSET);
        g.drawString("Q - Rotate Anticlockwise", LARGE_INSET, offset += TEXT_INSET);
        g.drawString("E - Rotate Clockwise", LARGE_INSET, offset += TEXT_INSET);
        g.drawString("S - Drop", LARGE_INSET, offset += TEXT_INSET);
        g.drawString("P - Pause Game", LARGE_INSET, offset += TEXT_INSET);

        //preview field
        g.setFont(LARGE_FONT);
        g.drawString("Next Tile: ", SMALL_INSET, 70);
        g.drawRect(SQUARE_CENTER_X - PREVIEW_SIZE, SQUARE_CENTER_Y - PREVIEW_SIZE,
                PREVIEW_SIZE * 2, PREVIEW_SIZE * 2);

        //draw next tile in the preview box
        Tile tile = tetris.getNextTile();
        if(tetris.isGameOver() && tile != null){
            //get size properties of new tile
            Integer columns = tile.getColumns();
            Integer rows = tile.getRows();
            Integer dimension = tile.getDimension();

            //calculate center point of starting draw process for tile
            Integer startX = (SQUARE_CENTER_X - (columns * TILE_SIZE / 2));
            Integer startY = (SQUARE_CENTER_Y - (rows * TILE_SIZE / 2));

            //get insets for preview. Rotation for preview is set by default 0
            Integer topInsert = tile.getTopInsert(0);
            Integer leftInsert = tile.getLeftInsert(0);

            //loop through pieces of tile and make it appear on preview box
            for(int row = 0; row < dimension; row++)
                for(int column = 0; column < dimension; column++)
                    if(tile.isTile(column, row, 0))
                        drawTile(tile, startX + (column + leftInsert) * TILE_SIZE,
                                startY + (row - topInsert) * TILE_SIZE, g);
        }
    }

    /**
     * Draw a tile for preview box
     * @param tile Tile that needs to be drawn
     * @param x Coordinate x for drawing
     * @param y Coordinate y for drawing
     * @param g Graphics entity
     */
    private void drawTile(Tile tile, Integer x, Integer y, Graphics g) {

    }
}
