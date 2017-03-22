package graphoney.rendering;

import graphoney.core.systems.FrameAction;
import graphoney.core.systems.System;
import graphoney.utils.logging.Logger;

import java.util.List;

public class RenderingSystem extends System {

    private static final String NAME = "rendering";
    private static final RenderingAction ACTION = new RenderingAction();

    private static class RenderingAction implements FrameAction {
        @Override
        public void run() throws InterruptedException {
            Logger.printInfo("Rendering...");
            Thread.sleep(1000);
        }
    }

    public RenderingSystem() {
        super(NAME, ACTION);
    }

    @Override
    public void transferData(Object data) throws IllegalArgumentException {
        if (data instanceof Integer) {

        } else {
            throw new IllegalArgumentException();
        }
    }
}
