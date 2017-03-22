package graphoney.core.systems;

/**
 * Represents an action that must be performed by system of engine cyclically on each frame.
 */
@FunctionalInterface
public interface FrameAction {

    /**
     * Contains instructions that the system must perform on each frame.
     * @throws InterruptedException if interrupted while waiting.
     */
    void run() throws InterruptedException;

}
