package graphoney.core;

import graphoney.core.events.SystemEvent;
import graphoney.core.events.SystemEventListener;
import graphoney.core.systems.SystemManager;
import graphoney.utils.logging.Logger;

public class Init {

    public static void main(String[] args) throws InterruptedException {
        SystemEventListener eventListener = SystemEventListener.getInstance();
        eventListener.initiate(SystemEvent.INITIALIZATION);
        Thread.sleep(5000);
        eventListener.initiate(SystemEvent.TERMINATION);
        SystemManager.getInstance().waitForSystemsTermination();
        eventListener.stop();
        Logger.close();
    }

}
