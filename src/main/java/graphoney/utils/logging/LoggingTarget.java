package graphoney.utils.logging;

public enum LoggingTarget {
    DEBUG(1, "DEBUG: "),
    INFO(2, ""),
    WARNING(3, "WARNING: "),
    ERROR(4, "ERROR: ");

    private int level;
    private String prefix;

    LoggingTarget(int level, String prefix) {
        this.level = level;
        this.prefix = prefix;
    }

    public int getLevel() {
        return level;
    }

    public String getPrefix() {
        return prefix;
    }

}
