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

    /**
     * Shows if game clock is stopped
     * @return Shows if game is stopped
     */
    boolean isPaused();

    /**
     * Checks if cycle has elapsed for this clock and decrements cycles if their amount is greater than 0
     * @return Is cycle elapsed or not
     */
    boolean hasElapsedCycle();

    /**
     * Checks if cycle has elapsed for this clock
     * @return Is cycle elapsed or not
     */
    boolean peekElapsedCycle();

    /**
     * Calculates current time using the computer's high resolution clock
     * @return Current time in milliseconds
     */
    static long getCurrentTime() {
        return (System.nanoTime() / 1000000L);
    }
}
