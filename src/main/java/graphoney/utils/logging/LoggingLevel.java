package graphoney.utils.logging;

public enum LoggingLevel {
    DEBUG(1, "DEBUG: "),
    INFO(2, ""),
    WARNING(3, "WARNING: "),
    ERROR(4, "ERROR: ");

    private int level;
    private String prefix;

    LoggingLevel(int level, String prefix) {
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
