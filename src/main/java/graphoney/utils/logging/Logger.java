package graphoney.utils.logging;

import java.io.*;
import java.util.Calendar;

public class Logger {

    private static final String LOG_PATH;

    private static PrintStream out;

    private static LoggingLevel currentLevel = LoggingLevel.DEBUG;

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

    public static void setLoggingLevel(LoggingLevel level) {
        currentLevel = level;
    }

    public static void log(LoggingLevel level, String message) {
        if (level.getLevel() >= currentLevel.getLevel()) {
            out.println(level.getPrefix() + message);
        }
    }

    public static void close() {
        if (out != null) {
            out.flush();
            out.close();
            out = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
