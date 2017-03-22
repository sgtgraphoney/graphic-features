package graphoney.core.systems;

/**
 * Represents the thread that the system of engine uses to run.
 */
public class SystemThread extends Thread {

    private FrameAction target;
    private boolean active;

    /**
     * Constructs a new thread for functional system of engine.
     * @param target the action that must be performed cyclically on each frame.
     */
    public SystemThread(FrameAction target) {
        this.target = target;
    }

    /**
     * Starts the thread.
     */
    @Override
    public synchronized void start() {
        active = true;
        super.start();
    }

    /**
     * Cyclically performs the action until the thread is interrupted.
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            if (active) {
                try {
                    target.run();
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }

    /**
     * Returns the boolean value that indicates whether the action is performed.
     * @return the value of the <tt>active</tt> flag.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the <tt>active</tt> flag to the specified value;
     * @param active a new value of the flag.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
