package Tetris;

import BoardPanel.BoardPanelService;
import Tile.Tile;

import javax.swing.*;
import Clock.Clock;
import java.util.Random;

public class TetrisSettings extends JFrame {

    TetrisSettings(String line) {
        super(line);
    }
    /**
     * How many milliseconds it takes per frame
     */
    private static final Long FRAME_TIME = 1000L / 50L;

    /**
     * Amount of tiles that there can be
     */
    private static final Integer TYPE_COUNT = Tile.values().length;

    /**
     * BoardPanel instance
     */
    private BoardPanelService boardPanelService;

    /**
     * SidePanelInstance
     */
    private SidePanel side;

    /**
     * Is game over or not
     */
    private Boolean isGameOver;

    /**
     * Is game paused or not
     */
    private Boolean isPaused;

    /**
     * Checks if game was played (becomes false automatically when game starts
     */
    private Boolean isNewGame;

    /**
     * Current level of game
     */
    private Integer level;

    /**
     * Current game score
     */
    private Integer score;

    /**
     * Random number generator used to make tile spawn randomly
     */
    private Random random;

    /**
     * Clock that handles the update logic
     */
    private Clock logicTimer;

    /**
     * Current tile
     */
    private Tile tile;

    /**
     * Next tile that will appear on screen
     */
    private Tile nextTile;

    /**
     * The current row of current tile
     */
    private Integer currentRow;

    /**
     * The current col of current tile
     */
    private Integer currentColumn;

    /**
     * The current rotation of tile
     */
    private Integer currentRotation;

    /**
     * Small amount of time that passes after spawn of tile and before its drop
     */
    private Integer dropCooldown;

    /**
     * Speed of game
     */
    private Float gameSpeed;

    public static Long getFrameTime() {
        return FRAME_TIME;
    }

    public static Integer getTypeCount() {
        return TYPE_COUNT;
    }

    public BoardPanelService getBoardPanelService() {
        return boardPanelService;
    }

    public void setBoardPanelService(BoardPanelService boardPanelService) {
        this.boardPanelService = boardPanelService;
    }

    public SidePanel getSide() {
        return side;
    }

    public void setSide(SidePanel side) {
        this.side = side;
    }

    public Boolean getGameOver() {
        return isGameOver;
    }

    public void setGameOver(Boolean gameOver) {
        isGameOver = gameOver;
    }

    public Boolean getNewGame() {
        return isNewGame;
    }

    public void setNewGame(Boolean newGame) {
        isNewGame = newGame;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Clock getLogicTimer() {
        return logicTimer;
    }

    public void setLogicTimer(Clock logicTimer) {
        this.logicTimer = logicTimer;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getNextTile() {
        return nextTile;
    }

    public void setNextTile(Tile nextTile) {
        this.nextTile = nextTile;
    }

    public Integer getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(Integer currentRow) {
        this.currentRow = currentRow;
    }

    public Integer getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(Integer currentColumn) {
        this.currentColumn = currentColumn;
    }

    public Integer getCurrentRotation() {
        return currentRotation;
    }

    public void setCurrentRotation(Integer currentRotation) {
        this.currentRotation = currentRotation;
    }

    public Integer getDropCooldown() {
        return dropCooldown;
    }

    public void setDropCooldown(Integer dropCooldown) {
        this.dropCooldown = dropCooldown;
    }

    public Float getGameSpeed() {
        return gameSpeed;
    }

    public void setGameSpeed(Float gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public Boolean getPaused() { return isPaused; }

    public void setPaused(Boolean paused) { isPaused = paused; }
}
