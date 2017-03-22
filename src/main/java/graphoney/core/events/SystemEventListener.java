package graphoney.core.events;

public class SystemEventListener {

    private static final SystemEventListener HANDLER = new SystemEventListener();

    public static SystemEventListener getInstance() {
        return HANDLER;
    }

    private SystemEventHandlingRouter handlerThread = new SystemEventHandlingRouter();

    private SystemEventListener() {
        handlerThread.start();
    }

    public void initiate(SystemEvent event) {
        handlerThread.route(event);
    }

    public void stop() {
        try {
            handlerThread.interrupt();
            handlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
