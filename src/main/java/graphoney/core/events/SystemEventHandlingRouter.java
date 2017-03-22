package graphoney.core.events;

import graphoney.rendering.RenderingSystem;
import graphoney.core.systems.SystemManager;
import graphoney.utils.logging.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

class SystemEventHandlingRouter extends Thread {

    private Map<SystemEvent, SystemEventHandler> handlers = new HashMap<>();
    private LinkedBlockingQueue<SystemEvent> eventQueue = new LinkedBlockingQueue<>();

    {
        handlers.put(SystemEvent.INITIALIZATION,
                () -> SystemManager.getInstance().runSystems(new RenderingSystem())
        );

        handlers.put(SystemEvent.TERMINATION,
                () -> SystemManager.getInstance().terminateAllSystems()
        );
    }

    @Override
    public void run() {
        while (!isInterrupted()) {

            SystemEvent event;
            try {
                event = eventQueue.take();
            } catch (InterruptedException e) {
                Logger.printInfo("System event handler was interrupted.");
                event = SystemEvent.TERMINATION;
            }

            handlers.get(event).handle();

        }
    }

    public void route(SystemEvent event) {
        eventQueue.add(event);
    }

}
