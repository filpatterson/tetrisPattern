package Clock;

public class Clock implements ClockInterface {
    /**
     * Amount of milliseconds per one cycle
     */
    private Float millisPerCycle = 0.0f;

    /**
     * The last time when the clock was updated (used for calculation of delta time)
     */
    private Long lastUpdate = 0L;

    /**
     * Amount of cycles that have elapsed and haven't been polled
     */
    private Integer elapsedCycles = 0;

    /**
     * Amount of time towards the next elapsed cycle
     */
    private Float excessCycles = 0.0f;

    /**
     * Is game paused or not
     */
    private Boolean isPaused = false;

    public Clock(Float cyclesPerSecond){
        setCyclesPerSecond(cyclesPerSecond);
        reset();
    }

    public void setCyclesPerSecond(Float cyclesPerSecond) { this.millisPerCycle = (1.0f / cyclesPerSecond) * 1000; }

    public void reset() {
        this.elapsedCycles = 0;
        this.excessCycles = 0.0f;
        this.lastUpdate = getCurrentTime();
        this.isPaused = false;
    }

    public void update(){
        //get current time and calculate delta time
        Long currentUpdate = getCurrentTime();
        Float delta = (float)(currentUpdate - lastUpdate) + excessCycles;

        //update amount of elapsed cycles and excess cycles if game is not paused
        if(!isPaused) {
            this.elapsedCycles += (int)Math.floor(delta / millisPerCycle);
            this.excessCycles = delta % millisPerCycle;
        }

        //set the last update time for next update cycle
        this.lastUpdate = currentUpdate;
    }

    public void setPaused(Boolean paused){
        this.isPaused = paused;
    }

    public Boolean isPaused(){
        return this.isPaused;
    }

    public Boolean hasElapsedCycle(){
        if(elapsedCycles > 0){
            elapsedCycles--;
            return true;
        }
        return false;
    }

    public Boolean peekElapsedCycle(){
        return (elapsedCycles > 0);
    }

    public static final Long getCurrentTime(){
        return (System.nanoTime() / 1000000L);
    }
}
