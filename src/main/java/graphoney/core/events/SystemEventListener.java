package graphoney.core.events;

import graphoney.core.systems.SystemManager;
import graphoney.rendering.RenderingSystem;
import graphoney.utils.logging.Logger;

/**
 * Represents the system event listener that receives events and transfers them to handling thread.
 * All handlers running in the additional thread, an instance of {@link SystemEventHandlingThread}.
 */
public class SystemEventListener {

    private static final SystemEventListener HANDLER = new SystemEventListener();

    /**
     * Returns the single instance of the class.
     * @return the single instance of the class.
     */
    public static SystemEventListener getInstance() {
        return HANDLER;
    }

    private SystemEventHandlingThread eventHandlingThread = new SystemEventHandlingThread();

    private SystemEventListener() {
        registerHandlers();
        eventHandlingThread.start();
    }

    private void registerHandlers() {

        eventHandlingThread.registerSystemEventHandler(SystemEvent.INITIALIZATION,
                () -> SystemManager.getInstance().runSystems(new RenderingSystem())
        );

        eventHandlingThread.registerSystemEventHandler(SystemEvent.TERMINATION,
                () -> SystemManager.getInstance().terminateAllSystems()
        );

    }

    /**
     * Initiates the specified event.
     * @param event event that must be initiated.
     */
    public void initiate(SystemEvent event) {
        eventHandlingThread.pushEvent(event);
    }

    /**
     * Stops the thread of event handling and waits for its finalization.
     */
    public void stop() {
        try {
            eventHandlingThread.interrupt();
            eventHandlingThread.join();
        } catch (InterruptedException e) {
            Logger.printError("System event listener was interrupted.");
        }
    }

}
