package graphoney.core.systems;

/**
 * Represents the action that must be performed by system cyclically in the system thread.
 */
@FunctionalInterface
public interface LoopAction {

    /**
     * Contains instructions that the system must perform.
     *
     * @param delta the time elapsed since the previous action.
     * @throws InterruptedException if interrupted while waiting.
     */
    void perform(double delta) throws InterruptedException;

}
