package graphoney.core.systems;

import graphoney.utils.logging.Logger;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import java.lang.*;

/**
 * Represents the thread that the system of engine uses to perform.
 */
public class SystemThread extends Thread {

    private final LoopAction target;
    private final ServiceAction initialization, finalization;
    private volatile boolean active;
    private volatile boolean interrupted;
    private long lastFrameTime, lastFps;
    private double delta;
    private int threadFps;

    /**
     * Constructs a new thread for functional system of engine.
     *
     * @param target         the action that must be performed cyclically on each frame.
     * @param initialization the action that must be performed for initialization of the system.
     * @param finalization   the action that must be performed for finalization of the system.
     */
    public SystemThread(LoopAction target, ServiceAction initialization, ServiceAction finalization) {
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

            initialization.service();

            while (!interrupted) {

                updateDeltaAndFps();

                if (active) {
                    target.perform(delta);
                }

            }

        } catch (InterruptedException e) {

            Logger.printInfo("System thread is interrupted.");

        } finally {

            try {
                finalization.service();
            } catch (InterruptedException e) {
                Logger.printError("Finalizing of the system thread is interrupted.");
            }

            Logger.printInfo("System is stopped.");

        }

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

    /**
     * Returns the FPS value of the thread.
     * FPS shows how many times the main action of the thread performs.
     *
     * @return FPS.
     */
    public int getThreadFps() {
        return threadFps;
    }

    @Override
    public void interrupt() {
        interrupted = true;
        super.interrupt();
    }

    private static final int MILLIS_IN_SECOND = 1000;

    private void updateDeltaAndFps() {
        long currentFrameTime = getCurrentTime();
        delta = (double) (currentFrameTime - lastFrameTime) / MILLIS_IN_SECOND;

        if (currentFrameTime - lastFps > MILLIS_IN_SECOND) {
            Display.setTitle("FPS: " + threadFps);
            threadFps = 0;
            lastFps += MILLIS_IN_SECOND;
        }
        threadFps++;

        lastFrameTime = currentFrameTime;
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

}
