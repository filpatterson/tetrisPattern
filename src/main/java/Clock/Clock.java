package Clock;

public class Clock implements ClockInterface {
    /**
     * Amount of milliseconds per one cycle
     */
    private float millisPerCycle = 0.0f;

    /**
     * The last time when the clock was updated (used for calculation of delta time)
     */
    private long lastUpdate = 0L;

    /**
     * Amount of cycles that have elapsed and haven't been polled
     */
    private int elapsedCycles = 0;

    /**
     * Amount of time towards the next elapsed cycle
     */
    private float excessCycles = 0.0f;

    /**
     * Is game paused or not
     */
    private boolean isPaused = false;

    public Clock(float cyclesPerSecond){
        setCyclesPerSecond(cyclesPerSecond);
        reset();
    }

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

    public boolean isPaused(){
        return this.isPaused;
    }

    public boolean hasElapsedCycle(){
        if(elapsedCycles > 0){
            elapsedCycles--;
            return true;
        }
        return false;
    }

    public boolean peekElapsedCycle(){
        return (elapsedCycles > 0);
    }

    public static long getCurrentTime(){
        return (System.nanoTime() / 1000000L);
    }
}
