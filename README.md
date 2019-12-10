# Laboratory work Nr.2

<h2>Tetris</h2>

<h3>Members Group:</h3>
1. Cretu Dumitru
2. Ejova Ecaterina
3. Galaju Elizabet
4. Scebec Mihai

Tetris is an aspect-oriented implementation of the well-known game.

*Side Panel* - a side panel that displays the control buttons, the number of points and the level on the screen.

*Clock* is responsible for the internal implementation of the clock and speeds up the flow of time with each new level. When paused, he stops time.

*BoardPanel* - a panel on which the playing field is drawn, all the pieces and their fall.

*Tetris* is the base. Here game logic and mechanics in action.

*Tile* - pieces of what they consist of, how to show them and how they can change.


Creating and Drawing the falling piece:

*Piece*: 

There are seven pieces in standard Tetris.

![pieces](https://github.com/filpatterson/tetrisPattern/blob/master/pieces.jpg)

Each standard piece is composed of four blocks. The two "L" and "dog" pieces are mirror
images of each other, but we'll just think of them as similar but distinct pieces. A chemist
might say that they where "isomers" or more accurately "enantiomers".

A piece can be rotated 90˚ counter-clockwise to yield another piece. Enough rotations get
you back to the original piece — for example rotating a dog twice brings you back to the
original state. Essentially, each tetris piece belongs to a family of between one and four
distinct rotations. The square has one, the dogs have two, and the L's have four. For
example, here are the four rotations (going counter-clockwise) of the left hand L:


![tetris3](https://github.com/filpatterson/tetrisPattern/blob/master/tetris3.PNG)


_Creating and drawing the board:_
```java
public SidePanel(Tetris tetris){
        this.tetris = tetris;
        setPreferredSize(new Dimension(200, BoardPanelSettings.PANEL_HEIGHT));
        setBackground(Color.BLACK);
}
```

The goal for this step is to select a random falling piece.

_Defining the piece types:_

In this design, the falling piece is represented by  array of booleans, indicating whether the given cell is or is not painted in this piece.  For example, here is the definition of an I piece in the file *Tile.java*, package *Tile*:
```java
TypeI(new Color(BoardPanelSettings.COLOR_MIN, BoardPanelSettings.COLOR_MAX, BoardPanelSettings.COLOR_MAX),
            4, 4, 1, new boolean[][] {
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
```

_Add new piece in the file *BoardPanelServices*:_
```java
public void addPiece(Tile tile, int x, int y, int rotation) {
        //  loop through all pieces of tile and add them to the board only if boolean that represents that piece is set
        //to true
        for(int col = 0; col < tile.getDimension(); col++)
            for(int row = 0; row < tile.getDimension(); row++)
                if(tile.isTile(col, row, rotation))
                    setTile(col + x, row + y, tile);
    }
```

_Writing new falling piece:_

The _paintComponent()_ method is responsible for randomly choosing a new piece in the file *BoardPanelServices*:
 ```java
    // draw pieces of tile on board
            for(int x = 0; x < BoardPanelSettings.COL_COUNT; x++)
                for(int y = BoardPanelSettings.HIDDEN_ROW_COUNT; y < BoardPanelSettings.ROW_COUNT; y++) {
                    Tile tile = getTile(x, y);
                    if(tile != null)
                        drawTile(tile, x * BoardPanelSettings.TILE_SIZE_PIXELS,
                                (y - BoardPanelSettings.HIDDEN_ROW_COUNT) * BoardPanelSettings.TILE_SIZE_PIXELS, g);
                }
```


_Painting the falling piece:_

To paint the falling piece (in the paintComponent method), we iterate over each cell and if the value of that cell is "true", then we should paint it:
```java
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
```

_Moving the falling piece left/right/down:_

In this step, we have our falling piece respond to 
left-[A], right-[D], rotate-anticlockwise-[Q], rotate-clockwise-[E], pause-[P] and drop-[S] key presses by moving in the given direction.  
```java
@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //set color for drawing
        g.setColor(DRAW_COLOR);

        //variable for handling Y position of string or element
        int offset;

        //stats info
        g.setFont(LARGE_FONT);
        g.drawString("STATS", SMALL_INSET, offset = STATS_INSET);
        g.setFont(SMALL_FONT);
        g.drawString("Level: " + tetris.getLevel(), LARGE_INSET, offset += TEXT_INSET);
        g.drawString("Score: " + tetris.getScore(), LARGE_INSET, offset += TEXT_INSET);

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
        if(!tetris.isGameOver() && tile != null){
            //get size properties of new tile
            int columns = tile.getColumns();
            int rows = tile.getRows();
            int dimension = tile.getDimension();

            //calculate center point of starting draw process for tile
            int startX = (SQUARE_CENTER_X - (columns * TILE_SIZE / 2));
            int startY = (SQUARE_CENTER_Y - (rows * TILE_SIZE / 2));

            //get insets for preview. Rotation for preview is set by default 0
            int topInsert = tile.getTopInsert(0);
            int leftInsert = tile.getLeftInsert(0);

            //loop through pieces of tile and make it appear on preview box
            for(int row = 0; row < dimension; row++)
                for(int column = 0; column < dimension; column++)
                    if(tile.isTile(column, row, 0))
                        drawTile(tile, startX + (column + leftInsert) * TILE_SIZE,
                                startY + (row - topInsert) * TILE_SIZE, g);
        }

    }
```
_Removing Full Rows:_

_checkLines()_ check if there is more than one line filled and without spaces, so the program clears them.
_checkLine()_ check if there is at least one line filled and without spaces so that the program clears it.

```java
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
```

*Creational Design Pattern: Singleton*

Singleton is a creational design pattern that lets you ensure that a class has only one instance, while providing a global access point to this instance.

```java
package Tetris;

//Singleton design pattern instance of Tetris
    public static Tetris instance = new Tetris();

    //Returns the instance of Tetris
    public static Tetris getInstance() {
        return instance;
    }


public static void main(String[] args) {
        //Singleton GUI
        Tetris tetris = Tetris.getInstance();
        tetris.startGame();
    }
```
*Structural Design Pattern: Decorator*

The Decorator pattern is used to alter an individual instance of a class at runtime, by creating a decorator class which wraps the original class.
This way, changing or adding functionalities of the decorator object won't affect the structure or the functionalities of the original object.

```java
package Clock;

interface ClockInterface {
    /**
     * Sets amount of cycles that can be elapsed per second
     * @param cyclesPerSecond Amount of cycles that can be elapsed per second
     */
    void setCyclesPerSecond(float cyclesPerSecond);

    /**
     * Resets all clock stats
     */
    void reset();

    /**
     * Updates the clock stats
     */
    void update();

    /**
     * Pauses and unpauses clock for game.
     * @param paused Clocks needs to be paused or not
     */
    void setPaused(boolean paused);
```

```java
package Clock;

public void setCyclesPerSecond(float cyclesPerSecond) { this.millisPerCycle = (1.0f / cyclesPerSecond) * 1000; }

    public void reset() {
        this.elapsedCycles = 0;
        this.excessCycles = 0.0f;
        this.lastUpdate = getCurrentTime();
        this.isPaused = false;
    }

    public void update(){
        //get current time and calculate delta time
        long currentUpdate = getCurrentTime();
        float delta = (float)(currentUpdate - lastUpdate) + excessCycles;

        //update amount of elapsed cycles and excess cycles if game is not paused
        if(!isPaused) {
            this.elapsedCycles += (int)Math.floor(delta / millisPerCycle);
            this.excessCycles = delta % millisPerCycle;
        }

        //set the last update time for next update cycle
        this.lastUpdate = currentUpdate;
    }

    public void setPaused(boolean paused){
        this.isPaused = paused;
    }
```

*Filter Pattern*
We apply the Filter Pattern by separating the characteristics of certain classes and methods that will be used in them from their immediate implementation.

*Bridge Pattern*
The bridge pattern is used in the Tetris class, which is a combination of all classes and acts as a link between the gameplay and the interface, using all its parts.


Our application:

![tetris1](https://github.com/filpatterson/tetrisPattern/blob/master/tetris1.PNG)

![tetris2](https://github.com/filpatterson/tetrisPattern/blob/master/tetris2.PNG)
