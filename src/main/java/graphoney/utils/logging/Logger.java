package graphoney.utils.logging;

import java.io.*;
import java.util.Calendar;

public class Logger {

    private static final String LOG_PATH;

    private static PrintStream out;
    private static boolean logDate;

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
        String prefix;
        if (logDate) {
            prefix = getDateString() + ": ";
        } else {
            prefix = "";
        }
        out.println(prefix + message);
    }

    public static void printError(String message) {
        String prefix = "ERROR: ";
        if (logDate) {
            prefix += getDateString() + ": ";
        }
        out.println(prefix + message);
    }

    public static void close() {
        out.flush();
        out.close();
    }

    public static String getLogPath() {
        return LOG_PATH;
    }

    public static void setLogDate(boolean logDate) {
        Logger.logDate = logDate;
    }

    private static String getDateString() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE) + "." + (calendar.get(Calendar.MONTH) + 1) + "."
                + calendar.get(Calendar.YEAR) +  " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
    }

}
