package graphoney.rendering;

import graphoney.core.systems.System;

public class RenderingSystem extends System {

    private static final String NAME = "rendering";

    public RenderingSystem() {
        super(NAME);
    }

    @Override
    protected void initialize() throws InterruptedException {
        DisplayManager.createDisplay();
    }

    @Override
    protected void run() throws InterruptedException {
        DisplayManager.updateDisplay();
    }

    @Override
    protected void destroy() throws InterruptedException {
        DisplayManager.closeDisplay();
    }

    @Override
    public void transferData(Object data) throws IllegalArgumentException {
        if (data instanceof Integer) {

        } else {
            throw new IllegalArgumentException();
        }
    }
}
