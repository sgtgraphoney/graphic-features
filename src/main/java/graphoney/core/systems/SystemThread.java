package graphoney.core.systems;

public class SystemThread extends Thread {

    private FrameAction target;
    private boolean active = true;

    public SystemThread(FrameAction target) {
        this.target = target;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (active) {
                try {
                    target.run();
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
