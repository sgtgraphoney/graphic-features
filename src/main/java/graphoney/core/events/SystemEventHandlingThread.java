package graphoney.core.events;

import graphoney.utils.logging.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Represents the thread that event handling runs in.
 */
public class SystemEventHandlingThread extends Thread {

    private Map<SystemEvent, SystemEventHandler> handlers = new HashMap<>();
    private LinkedBlockingQueue<SystemEvent> eventQueue = new LinkedBlockingQueue<>();

    /**
     * Handles events.
     * If there is no events in the queue, then it waits for the new event.
     * Handling stops if the thread is interrupted.
     */
    @Override
    public void run() {
        while (!isInterrupted()) {

            SystemEvent event = null;
            try {
                event = eventQueue.take();
            } catch (InterruptedException e) {
                Logger.printInfo("System event handler was interrupted.");
                interrupt();
            }

            if (event != null) {
                handlers.get(event).handle();
            }

        }
    }

    /**
     * Registers the event handler for the specified event.
     *
     * @param event   type of the event.
     * @param handler what must be done.
     */
    public synchronized void registerSystemEventHandler(SystemEvent event, SystemEventHandler handler) {
        handlers.put(event, handler);
    }

    /**
     * Adds the event from the outside to the queue of events.
     * The event will be removed from the queue after handling.
     *
     * @param event event for handling.
     */
    public void pushEvent(SystemEvent event) {
        eventQueue.add(event);
    }

}
