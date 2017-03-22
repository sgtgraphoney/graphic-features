package graphoney.core.systems;

/**
 * Represents an action that must be performed by system in the system thread.
 */
@FunctionalInterface
public interface Action {

    /**
     * Contains instructions that the system must perform.
     * @throws InterruptedException if interrupted while waiting.
     */
    void perform() throws InterruptedException;

}
