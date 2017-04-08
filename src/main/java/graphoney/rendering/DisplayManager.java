package graphoney.rendering;

import graphoney.utils.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager {

    private static final int FPS_CAP = 120;

    private static int displayWidth = 1280;
    private static int displayHeight = 720;
    private static int subsampleAmount = 8;

    /**
     * Creates the display with the specified parameters.
     * Parameters should be specified with setters of this class.
     */
    public static void createDisplay() {

        ContextAttribs attribs = new ContextAttribs(3, 2);
        attribs.withForwardCompatible(true);
        attribs.withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            Display.create(new PixelFormat().withSamples(subsampleAmount));
            Display.setTitle("Look at me");
            GL11.glEnable(GL13.GL_MULTISAMPLE);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, displayWidth, displayHeight);

    }

    /**
     * Updates the display.
     * Should be invoked after updating all necessary parameters of rendered objects.
     */
    public static void updateDisplay() {
        Display.sync(FPS_CAP);
        Display.update();
    }

    /**
     * Closes the display.
     */
    public static void closeDisplay() {
        Display.destroy();
    }

    /**
     * Sets width of the display in pixels.
     *
     * @param displayWidth the number of pixels in width.
     */
    public static void setDisplayWidth(int displayWidth) {
        DisplayManager.displayWidth = displayWidth;
    }

    /**
     * Sets height of the display in pixels.
     *
     * @param displayHeight the number of pixels in height.
     */
    public static void setDisplayHeight(int displayHeight) {
        DisplayManager.displayHeight = displayHeight;
    }

    /**
     * Sets the amount of the subsamples for MSAA.
     *
     * @param subampleAmount the amount of subsamples.
     */
    public static void setSubsampleAmount(int subampleAmount) {
        DisplayManager.subsampleAmount = subampleAmount;
    }
}
