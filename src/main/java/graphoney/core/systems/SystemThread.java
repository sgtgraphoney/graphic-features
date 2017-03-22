package graphoney.core.systems;

import graphoney.utils.logging.Logger;

import java.lang.*;

/**
 * Represents the thread that the system of engine uses to perform.
 */
public class SystemThread extends Thread {

    private Action initialization, target, finalization;
    private boolean active;
    private boolean interrupted;

    /**
     * Constructs a new thread for functional system of engine.
     *
     * @param initialization the action that must be performed for initialization of the system.
     * @param target         the action that must be performed cyclically on each frame.
     * @param finalization   the action that must be performed for finalization of the system.
     */
    public SystemThread(Action initialization, Action target, Action finalization) {
        this.initialization = initialization;
        this.target = target;
        this.finalization = finalization;
    }

    /**
     * Starts the thread.
     */
    @Override
    public synchronized void start() {
        active = true;
        interrupted = false;
        super.start();
    }

    /**
     * Initializes the system.
     * Cyclically performs the action until the thread is interrupted.
     * Finalizes the system.
     */
    @Override
    public void run() {

        Logger.printInfo("System started.");

        try {

            initialization.perform();

            while (!interrupted) {
                if (active) {
                    target.perform();
                }
            }

        } catch (InterruptedException e) {
            Logger.printInfo("System thread was interrupted.");
        }

        try {
            finalization.perform();
        } catch (InterruptedException e) {
            Logger.printError("Finalizing of the system thread was interrupted.");
        }

        Logger.printInfo("System terminated.");

    }

    /**
     * Returns the boolean value that indicates whether the action is performed.
     *
     * @return the value of the <tt>active</tt> flag.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the <tt>active</tt> flag to the specified value;
     *
     * @param active a new value of the flag.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void interrupt() {
        interrupted = true;
        super.interrupt();
    }
}
