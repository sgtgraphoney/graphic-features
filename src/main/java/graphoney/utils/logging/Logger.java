package graphoney.utils.logging;

import java.io.*;

public class Logger {

    private static final String LOG_PATH;

    private static PrintStream out;

    static {
        String fileSeparator = System.getProperty("file.separator");
        LOG_PATH = "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "log.txt";

        File logFile = new File(LOG_PATH);
        try {
            if ((logFile.exists() && logFile.exists()) || logFile.createNewFile()) {
                FileOutputStream stream = new FileOutputStream(logFile);
                out = new PrintStream(stream);
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            System.err.println("Could not create log file. Logs will be redirected to stdout.");
            out = System.out;
        }
    }

    public static void printInfo(String message) {
        out.println(message);
    }

    public static void printError(String message) {
        out.println("ERROR: " + message);
    }

    public static String getLogPath() {
        return LOG_PATH;
    }

    public static void close() {
        out.flush();
        out.close();
    }

}
