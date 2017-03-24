package graphoney.core.systems;

/**
 * Represents the service action that must be performed by system in the system thread.
 */
@FunctionalInterface
public interface ServiceAction {

    /**
     * Contains instructions that the system must perform.
     *
     * @throws InterruptedException if interrupted while waiting.
     */
    void service() throws InterruptedException;

}
