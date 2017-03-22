package graphoney.utils.logging;

import graphoney.TestUtils;
import org.junit.Assert;
import org.junit.Test;

public class LoggerTest {

    @Test
    public void printInfo() throws Exception {
        String message = "test message";
        Logger.printInfo(message);
        String input = TestUtils.readLastLineFromFile(Logger.getLogPath());
        Assert.assertTrue(input.equals(message));
    }

    @Test
    public void printError() throws Exception {
        String message = "error message";
        Logger.printError(message);
        String input = TestUtils.readLastLineFromFile(Logger.getLogPath());
        Assert.assertTrue(input.equals("ERROR: " + message));
    }

}