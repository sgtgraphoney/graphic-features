package graphoney.core.systems;

import graphoney.utils.logging.Logger;

public abstract class System {

    private String name;
    private SystemThread thread;

    public System(String name, FrameAction action) {
        this.name = name;
        thread = new SystemThread(action);
    }

    public void start() {
        Logger.printInfo("Starting system '" + name + "'...");
        thread.start();
    }

    public void pause() {
        Logger.printInfo("Pausing system '" + name + "'...");
        thread.setActive(false);
    }

    public void resume() {
        Logger.printInfo("Resuming system '" + name + "'...");
        thread.setActive(true);
    }

    public void terminate() {
        Logger.printInfo("Terminating system '" + name + "'...");
        thread.interrupt();
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return thread.isActive();
    }

    public boolean isTerminated() {
        return !thread.isAlive();
    }

    public abstract void transferData(Object data) throws IllegalArgumentException;

}
