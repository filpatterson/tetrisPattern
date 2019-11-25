package Tetris;

import java.util.Random;

public class Tetris extends TetrisSettings implements TetrisInterface {
    private TetrisSettings game = new Tetris();

    void startGame() {
        //initialize random number generator
        game.setRandom(new Random());
        game.setNewGame(true);

    }
}
