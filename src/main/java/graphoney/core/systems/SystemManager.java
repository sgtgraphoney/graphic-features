package graphoney.core.systems;

import graphoney.utils.logging.Logger;
import graphoney.utils.logging.LoggingLevel;

import java.util.HashMap;
import java.util.Map;

public class SystemManager {

    private static final SystemManager INSTANCE = new SystemManager();

    public static SystemManager getInstance() {
        return INSTANCE;
    }

    private final Map<String, System> systems = new HashMap<>();

    private SystemManager() {
        try {
            SystemLoader loader = new SystemLoader();
            systems.putAll(loader.loadSystems());
        } catch (InstantiationException e){
            Logger.log(LoggingLevel.ERROR, "Could not load systems.");
        }
    }



}
