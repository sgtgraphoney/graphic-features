package graphoney.core.events;

/**
 * A handler of the system event.
 */
@FunctionalInterface
public interface SystemEventHandler {

    /**
     * Performs actions required to handle the event.
     */
    void handle();

}
