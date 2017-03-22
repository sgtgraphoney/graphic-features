package graphoney.core.systems;

import graphoney.utils.logging.Logger;

/**
 * <p>Represents the functional system of engine. Each implementation of system must extend this class.</p>
 * <p>The system works in its own thread, that is an instance of {@link SystemThread}.</p>
 */
public abstract class System {

    private String name;
    private SystemThread thread;

    /**
     * Constructs a new system with specified parameters.
     *
     * @param name the name of the system.
     */
    public System(String name) {
        this.name = name;
        thread = new SystemThread(this::initialize, this::run, this::destroy);
    }

    /**
     * Initializes the system.
     *
     * @throws InterruptedException if interrupted while waiting.
     */
    protected abstract void initialize() throws InterruptedException;

    /**
     * The main action that is performed cyclically on each frame.
     *
     * @throws InterruptedException if interrupted while waiting.
     */
    protected abstract void run() throws InterruptedException;

    /**
     * Destroys the system.
     *
     * @throws InterruptedException if interrupted while waiting.
     */
    protected abstract void destroy() throws InterruptedException;

    /**
     * Starts the thread of the system.
     */
    public void start() {
        Logger.printInfo("Starting system '" + name + "'...");
        thread.start();
    }

    /**
     * Suspends execution of the frame action.
     * Does not interrupts the thread.
     */
    public void pause() {
        Logger.printInfo("Pausing system '" + name + "'...");
        thread.setActive(false);
    }

    /**
     * Resumes execution of the frame action.
     * Does not resumes the thread.
     */
    public void resume() {
        Logger.printInfo("Resuming system '" + name + "'...");
        thread.setActive(true);
    }

    /**
     * Interrupts the thread of the system and stops the system.
     */
    public void terminate() {
        Logger.printInfo("Terminating system '" + name + "'...");
        thread.interrupt();
    }

    /**
     * Returns the name of the current system.
     *
     * @return the name of the system.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the boolean value that indicates whether the system runs.
     *
     * @return the value of the <tt>active</tt> flag.
     */
    public boolean isActive() {
        return thread.isActive();
    }

    /**
     * Returns a boolean value that indicates whether the system is terminated and the thread is not alive.
     *
     * @return a boolean value that indicates whether the system is terminated.
     */
    public boolean isTerminated() {
        return !thread.isAlive();
    }

    /**
     * Waits for system shutdown.
     */
    public void waitForTermination() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Logger.printError("Waiting for termination of system '" + name + "' interrupted.");
        }
    }

    /**
     * Transfers any data to the current system.
     * This is the only way to transfer data from one system to another.
     *
     * @param data data that is required for the system.
     * @throws IllegalArgumentException if the data has illegal type for the current system.
     */
    public abstract void transferData(Object data) throws IllegalArgumentException;

}
