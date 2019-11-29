package BoardPanel;

import java.awt.*;
import Tile.Tile;
import Tetris.Tetris;

import javax.swing.*;

public class BoardPanelService extends JPanel implements BoardPanelInterface{
    /**
     * Tiles that sets the board
     */
    private Tile[][] tiles;
    private Tetris tetris;

    public BoardPanelService(Tetris tetris) {
        this.tetris = tetris;
        this.tiles = new Tile[BoardPanelSettings.ROW_COUNT][BoardPanelSettings.COL_COUNT];

        setPreferredSize(new Dimension(BoardPanelSettings.PANEL_WIDTH, BoardPanelSettings.PANEL_HEIGHT));
        setBackground(Color.BLACK);
    }

    public void clear(){
        //loop through all board to clear it from any elements
        for(int i = 0; i < BoardPanelSettings.ROW_COUNT; i++)
            for(int j = 0; j < BoardPanelSettings.COL_COUNT; j++)
                tiles[i][j] = null;
    }

    public boolean isValidAndEmpty(Tile tile, int x, int y, int rotation) {
        //make sure that column for placing tile is valid
        if(x < -tile.getLeftInsert(rotation)
                || x + tile.getDimension() - tile.getRightInsert(rotation) >= BoardPanelSettings.COL_COUNT)
            return false;

        //make sure that row for placing row is valid
        if(y < -tile.getTopInsert(rotation)
                || y + tile.getDimension() - tile.getBottomInsert(rotation) >= BoardPanelSettings.ROW_COUNT)
            return false;

        // loop through all pieces to to see if there are any conflicts with already existing tile
        for(int col = 0; col < tile.getDimension(); col++)
            for(int row = 0; row < tile.getDimension(); row++)
                if(tile.isTile(col, row, rotation) && isOccupied(x + col, y + row))
                    return false;

        return true;
    }

    public void addPiece(Tile tile, int x, int y, int rotation) {
        //  loop through all pieces of tile and add them to the board only if boolean that represents that piece is set
        //to true
        for(int col = 0; col < tile.getDimension(); col++)
            for(int row = 0; row < tile.getDimension(); row++)
                if(tile.isTile(col, row, rotation))
                    setTile(col + x, row + y, tile);
    }

    public int checkLines() {
        int completedLines = 0;

        for(int row = 0; row < BoardPanelSettings.ROW_COUNT; row++)
            if(checkLine(row))
                completedLines++;

        return completedLines;
    }

    public boolean checkLine(int line) {
        // make sure that current line is not full
        for(int col = 0; col < BoardPanelSettings.COL_COUNT; col++)
            if(!isOccupied(col, line))
                return false;

        // if game founds out that there is filled line then this line needs to be removed
        for(int row = line - 1; row >= 0; row--)
            for(int col = 0; col < BoardPanelSettings.COL_COUNT; col++)
                setTile(col, row + 1, getTile(col, row));

        return true;
    }

    public boolean isOccupied(int x, int y) { return tiles[y][x] != null; }

    public void setTile(int x, int y, Tile tile) { tiles[y][x] = tile; }

    public Tile getTile(int x, int y) { return tiles[y][x]; }

    /**
     * Drawing elements for main panel of game
     * @param g Graphics instance
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.translate(BoardPanelSettings.BORDER_WIDTH, BoardPanelSettings.BORDER_WIDTH);

        // Draw the board differently depending on current game status
        if(tetris.isPaused()) {
            g.setFont(BoardPanelSettings.LARGE_FONT);
            g.setColor(Color.WHITE);
            String msg = "PAUSED";
            g.drawString(msg, BoardPanelSettings.CENTER_X - g.getFontMetrics().stringWidth(msg) / 2,
                    BoardPanelSettings.CENTER_Y);
        } else if (tetris.isNewGame() || tetris.isGameOver()){
            g.setFont(BoardPanelSettings.LARGE_FONT);
            g.setColor(Color.WHITE);

            // check either this is new game or game over and display required message on screen
            String msg = tetris.isNewGame() ? "TETRIS" : "POTRACENO";
            g.drawString(msg, BoardPanelSettings.CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 150);
            g.setFont(BoardPanelSettings.SMALL_FONT);
            msg = "Press Enter to play" + (tetris.isNewGame() ? "" : " Again");
            g.drawString(msg, BoardPanelSettings.CENTER_X - g.getFontMetrics().stringWidth(msg) / 2, 300);
        } else {

            // draw pieces of tile on board
            for(int x = 0; x < BoardPanelSettings.COL_COUNT; x++)
                for(int y = BoardPanelSettings.HIDDEN_ROW_COUNT; y < BoardPanelSettings.ROW_COUNT; y++) {
                    Tile tile = getTile(x, y);
                    if(tile != null)
                        drawTile(tile, x * BoardPanelSettings.TILE_SIZE_PIXELS,
                                (y - BoardPanelSettings.HIDDEN_ROW_COUNT) * BoardPanelSettings.TILE_SIZE_PIXELS, g);
                }

            // draw separately current tile (evade process of redrawing entire board)
            Tile tile = tetris.getTile();
            int tileCol = tetris.getTileColumn();
            int tileRow = tetris.getTileRow();
            int rotation = tetris.getTileRotation();
            for(int col = 0; col < tile.getDimension(); col++)
                for(int row = 0; row < tile.getDimension(); row++)
                    if(tileRow + row >= 2 && tile.isTile(col, row, rotation))
                        drawTile(tile,
                                (tileCol + col) * BoardPanelSettings.TILE_SIZE_PIXELS,
                                (tileRow + row - BoardPanelSettings.HIDDEN_ROW_COUNT)
                                        * BoardPanelSettings.TILE_SIZE_PIXELS, g);

            //draw ghost-tile that shows where can land current tile.
            Color base = tile.getBaseColor();
            base = new Color(base.getRed(), base.getGreen(), base.getBlue(), 20);

            for(int lowest = tileRow; lowest < BoardPanelSettings.ROW_COUNT; lowest++) {
                //if there's no collision detected, try the next row
                if(isValidAndEmpty(tile, tileCol, lowest, rotation))
                    continue;

                //draw figure one row higher than the one where collision takes place
                lowest--;

                //draw the ghost-tile
                for(int col = 0; col < tile.getDimension(); col++) {
                    for(int row = 0; row < tile.getDimension(); row++) {
                        if(lowest + row >= 2 && tile.isTile(col, row, rotation)) {
                            drawTile(base, base.brighter(), base.darker(),
                                    (tileCol + col) * BoardPanelSettings.TILE_SIZE_PIXELS,
                                    (lowest + row - BoardPanelSettings.HIDDEN_ROW_COUNT) * BoardPanelSettings.TILE_SIZE_PIXELS,
                                    g);

                        }
                    }
                }

                break;
            }

            //draw the background grid that will separate pieces of tiles
            g.setColor(Color.DARK_GRAY);
            for(int x = 0; x < BoardPanelSettings.COL_COUNT; x++) {
                for(int y = 0; y < BoardPanelSettings.VISIBLE_ROW_COUNT; y++) {
                    g.drawLine(0, y * BoardPanelSettings.TILE_SIZE_PIXELS,
                            BoardPanelSettings.COL_COUNT * BoardPanelSettings.TILE_SIZE_PIXELS,
                            y * BoardPanelSettings.TILE_SIZE_PIXELS);
                    g.drawLine(x * BoardPanelSettings.TILE_SIZE_PIXELS, 0,
                            x * BoardPanelSettings.TILE_SIZE_PIXELS,
                            BoardPanelSettings.VISIBLE_ROW_COUNT * BoardPanelSettings.TILE_SIZE_PIXELS);
                }
            }
        }

        //draw the outline of all interface
        g.setColor(Color.WHITE);
        g.drawRect(0, 0,
                BoardPanelSettings.TILE_SIZE_PIXELS * BoardPanelSettings.COL_COUNT,
                BoardPanelSettings.TILE_SIZE_PIXELS * BoardPanelSettings.VISIBLE_ROW_COUNT);
    }

    public void drawTile(Tile tile, int x, int y, Graphics g) {
        drawTile(tile.getBaseColor(), tile.getLightColor(), tile.getShadowColor(), x, y, g);
    }

    public void drawTile(Color base, Color light, Color dark, int x, int y, Graphics g) {
        //fill tile with base color
        g.setColor(base);
        g.fillRect(x, y, BoardPanelSettings.TILE_SIZE_PIXELS, BoardPanelSettings.TILE_SIZE_PIXELS);

        //fill bottom and right edges with shadow effects to make look like there is lightning system
        g.setColor(dark);
        g.fillRect(x, y + BoardPanelSettings.TILE_SIZE_PIXELS - BoardPanelSettings.SHADE_SIZE_PIXELS,
                BoardPanelSettings.TILE_SIZE_PIXELS, BoardPanelSettings.SHADE_SIZE_PIXELS);
        g.fillRect(x + BoardPanelSettings.TILE_SIZE_PIXELS - BoardPanelSettings.SHADE_SIZE_PIXELS,
                y, BoardPanelSettings.SHADE_SIZE_PIXELS, BoardPanelSettings.TILE_SIZE_PIXELS);

        //add lightning color to the tile
        g.setColor(light);
        for(int i = 0; i < BoardPanelSettings.SHADE_SIZE_PIXELS; i++) {
            g.drawLine(x, y + i, x + BoardPanelSettings.TILE_SIZE_PIXELS - i - 1, y + 1);
            g.drawLine(x + i, y, x + i, y + BoardPanelSettings.TILE_SIZE_PIXELS - i - 1);
        }
    }
}
